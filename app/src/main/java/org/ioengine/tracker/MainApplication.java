
package org.ioengine.tracker;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;

public class MainApplication extends Application {

    public static final String PRIMARY_CHANNEL = "default";

    @Override
    public void onCreate() {
        super.onCreate();
        System.setProperty("http.keepAliveDuration", String.valueOf(30 * 60 * 1000));

        migrateLegacyPreferences(PreferenceManager.getDefaultSharedPreferences(this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void registerChannel() {
        NotificationChannel channel = new NotificationChannel(
                PRIMARY_CHANNEL, getString(R.string.channel_default), NotificationManager.IMPORTANCE_MIN);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
    }

    private void migrateLegacyPreferences(SharedPreferences preferences) {
        String port = preferences.getString("port", null);
        if (port != null) {
            String host = preferences.getString("address", getString(R.string.settings_url_default_value));
            String scheme = preferences.getBoolean("secure", false) ? "https" : "http";

            Uri.Builder builder = new Uri.Builder();
            builder.scheme(scheme).encodedAuthority(host + ":" + port).build();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(MainFragment.KEY_URL, builder.toString());

            editor.remove("port");
            editor.remove("address");
            editor.remove("secure");
            editor.apply();
        }
    }

}
