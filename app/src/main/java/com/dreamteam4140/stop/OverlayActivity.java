package com.dreamteam4140.stop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dreamteam4140.stop.model.AppPreferences;

public class OverlayActivity extends AppCompatActivity {

    private String unlockPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);

        unlockPassword = AppPreferences.GetInstance(getApplicationContext()).getString(AppPreferences.Key.SETTINGS_PASSWORD_STR);
        //Toast.makeText(getApplicationContext(), "done! " + unlockPassword, Toast.LENGTH_LONG).show();
    }

    public void closeOverlay(View view)
    {
        finish();
    }
}
