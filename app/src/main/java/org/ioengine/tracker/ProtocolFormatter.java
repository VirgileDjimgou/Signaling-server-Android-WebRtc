
package org.ioengine.tracker;

import android.net.Uri;

public class ProtocolFormatter {

    public static String formatRequest(String url, Position position) {
        return formatRequest(url, position, null);
    }

    public static String formatRequest(String url, Position position, String alarm) {
        Uri serverUrl = Uri.parse(url);
        Uri.Builder builder = serverUrl.buildUpon()
                .appendQueryParameter("id", position.getDeviceId())
                .appendQueryParameter("timestamp", String.valueOf(position.getTime().getTime() / 1000))
                .appendQueryParameter("lat", String.valueOf(position.getLatitude()))
                .appendQueryParameter("lon", String.valueOf(position.getLongitude()))
                .appendQueryParameter("speed", String.valueOf(position.getSpeed()))
                .appendQueryParameter("bearing", String.valueOf(position.getCourse()))
                .appendQueryParameter("altitude", String.valueOf(position.getAltitude()))
                .appendQueryParameter("accuracy", String.valueOf(position.getAccuracy()))
                .appendQueryParameter("batt", String.valueOf(position.getBattery()));

        if (alarm != null) {
            builder.appendQueryParameter("alarm", alarm);
        }

        return builder.build().toString();
    }
}
