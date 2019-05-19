package com.dreamteam4140.stop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class TimerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent overlayIntent = new Intent();
        overlayIntent.setClassName("com.dreamteam4140.stop", "com.dreamteam4140.stop.OverlayActivity");
        overlayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        overlayIntent.putExtra("secondsRelax", intent.getIntExtra("secondsRelax", 0));
        context.startActivity(overlayIntent);


        /*PackageManager pm = context.getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage("com.dreamteam4140.stop");
        context.startActivity(launchIntent);
        Toast.makeText(context, "done!", Toast.LENGTH_LONG).show();*/
    }
}
