package com.dreamteam4140.stop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.dreamteam4140.stop.model.AppPreferences;

import org.florescu.android.rangeseekbar.RangeSeekBar;

public class SettingsShakeActivity extends AppCompatActivity {

    Switch _switchShakeSett;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_settings);

        _switchShakeSett = findViewById(R.id.switchShakeSett);

        _switchShakeSett.setChecked(AppPreferences.GetInstance(getApplicationContext()).getBool(AppPreferences.Key.SHAKER_ENABLED, false));

        _switchShakeSett.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppPreferences.GetInstance(getApplicationContext()).put(AppPreferences.Key.SHAKER_ENABLED, b);
                if (b) {
                    //do smth on switch On
                } else {
                    //do smth  on switch Off
                    //finish();
                }
            }
        });


        // Setup the new range seek bar
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<>(this);
        // Set the range
        rangeSeekBar.setRangeValues(15, 90);
        rangeSeekBar.setSelectedMinValue(20);
        rangeSeekBar.setSelectedMaxValue(88);

        // Add to layout
        //FrameLayout layout = (FrameLayout) findViewById(R.id.seekbar_placeholder);
        //layout.addView(rangeSeekBar);

        // Seek bar for which we will set text color in code
        //RangeSeekBar rangeSeekBarTextColorWithCode = (RangeSeekBar) findViewById(R.id.rangeSeekBarTextColorWithCode);
        //rangeSeekBarTextColorWithCode.setTextAboveThumbsColorResource(android.R.color.holo_blue_bright);
    }

    public void saveShakeSetting(View view) {
        finish();
    }
}
