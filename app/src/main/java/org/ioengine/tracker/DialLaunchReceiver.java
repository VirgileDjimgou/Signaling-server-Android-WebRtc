
package org.ioengine.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DialLaunchReceiver extends BroadcastReceiver {

    private static final String LAUNCHER_NUMBER = "8722227"; // TRACCAR

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        if (phoneNumber.equals(LAUNCHER_NUMBER)) {
            setResultData(null);
            Intent appIntent = new Intent(context, MainActivity.class);
            appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(appIntent);
        }
    }

}
