package com.dreamteam4140.stop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.dreamteam4140.stop.model.AppPreferences;

public class SettingsSliderActivity extends AppCompatActivity {

    Switch switchSliderSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_settings);

        switchSliderSettings = findViewById(R.id.switchSliderSett);

        switchSliderSettings.setChecked(AppPreferences.GetInstance(getApplicationContext()).getBool(AppPreferences.Key.SLIDER_ENABLED, false));

        switchSliderSettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppPreferences.GetInstance(getApplicationContext()).put(AppPreferences.Key.SLIDER_ENABLED, b);
                if (b) {
                    //do smth on switch On
                } else {
                    //do smth  on switch Off
                    //finish();
                }
            }
        });
    }

    public void saveSlider(View view)
    {
        finish();
    }
}
