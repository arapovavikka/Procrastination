package com.dreamteam4140.stop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamteam4140.stop.activity.ExitActivity;
import com.dreamteam4140.stop.model.AppPreferences;
import com.dreamteam4140.stop.service.Timer;

public class OverlayActivity extends AppCompatActivity {

    private String unlockPassword;

    private TextView timerText;

    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
        timer = new Timer(getResources());
        timerText = (TextView)findViewById(R.id.timerText);
        timer.start(timerText, 30);

        unlockPassword = AppPreferences.GetInstance(getApplicationContext()).getString(AppPreferences.Key.SETTINGS_PASSWORD_STR);
        //Toast.makeText(getApplicationContext(), "done! " + unlockPassword, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Do nothing or catch the keys you want to block
        return false;
    }
    public void closeOverlay(View view) {
        Intent navigationIntent = new Intent(this, UnlockPasswordActivity.class);
        startActivity(navigationIntent);
        //ExitActivity.exitApplication(getApplicationContext());
    }
}
