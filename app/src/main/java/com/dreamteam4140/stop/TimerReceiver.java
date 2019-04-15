package com.dreamteam4140.stop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class TimerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage("com.dreamteam4140.stop");
        context.startActivity(launchIntent);
        Toast.makeText(context, "done!", Toast.LENGTH_LONG).show();
    }
}
