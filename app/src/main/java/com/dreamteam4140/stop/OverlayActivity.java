package com.dreamteam4140.stop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        timer = new Timer();

        timerText = (TextView) findViewById(R.id.timerText);
        timer.start(timerText, 30);

        unlockPassword = AppPreferences.GetInstance(getApplicationContext()).getString(AppPreferences.Key.SETTINGS_PASSWORD_STR);
        //Toast.makeText(getApplicationContext(), "done! " + unlockPassword, Toast.LENGTH_LONG).show();
    }

    public void closeOverlay(View view) {
        finish();
    }
}
