
package org.ioengine.tracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.BatteryManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;

public class PositionProvider implements LostApiClient.ConnectionCallbacks, LocationListener {

    private static final String TAG = PositionProvider.class.getSimpleName();

    private static final int MINIMUM_INTERVAL = 1000;

    public interface PositionListener {
        void onPositionUpdate(Position position);
    }

    private final PositionListener listener;

    private final Context context;
    private SharedPreferences preferences;
    private LostApiClient apiClient;

    private String deviceId;
    private long interval;
    private double distance;
    private double angle;

    private Location lastLocation;

    private boolean started;

    public PositionProvider(Context context, PositionListener listener) {
        this.context = context;
        this.listener = listener;

        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        deviceId = preferences.getString(MainFragment.KEY_DEVICE, "undefined");
        interval = Long.parseLong(preferences.getString(MainFragment.KEY_INTERVAL, "600")) * 1000;
        distance = Integer.parseInt(preferences.getString(MainFragment.KEY_DISTANCE, "0"));
        angle = Integer.parseInt(preferences.getString(MainFragment.KEY_ANGLE, "0"));
    }

    public void startUpdates() {
        started = true;
        apiClient = new LostApiClient.Builder(context).addConnectionCallbacks(this).build();
        apiClient.connect();
    }

    private int getPriority(String accuracy) {
        switch (accuracy) {
            case "high":
                return LocationRequest.PRIORITY_HIGH_ACCURACY;
            case "low":
                return LocationRequest.PRIORITY_LOW_POWER;
            default:
                return LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected() {
        if (started) {
            LocationRequest request = LocationRequest.create()
                    .setPriority(getPriority(preferences.getString(MainFragment.KEY_ACCURACY, "medium")))
                    .setInterval(distance > 0 || angle > 0 ? MINIMUM_INTERVAL : interval);
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, request, this);
        } else {
            apiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null && (lastLocation == null
                || location.getTime() - lastLocation.getTime() >= interval
                || distance > 0 && DistanceCalculator.distance(location.getLatitude(), location.getLongitude(), lastLocation.getLatitude(), lastLocation.getLongitude()) >= distance
                || angle > 0 && Math.abs(location.getBearing() - lastLocation.getBearing()) >= angle)) {
            Log.i(TAG, "location new");
            lastLocation = location;
            listener.onPositionUpdate(new Position(deviceId, location, getBatteryLevel(context)));
        } else {
            Log.i(TAG, location != null ? "location ignored" : "location nil");
        }
    }

    @Override
    public void onConnectionSuspended() {
        Log.i(TAG, "lost client suspended");
    }

    public void stopUpdates() {
        if (apiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, this);
            apiClient.disconnect();
        }
        started = false;
    }

    public static double getBatteryLevel(Context context) {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (batteryIntent != null) {
            int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);
            return (level * 100.0) / scale;
        }
        return 0;
    }

}
