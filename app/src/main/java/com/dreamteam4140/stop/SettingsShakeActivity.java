package com.dreamteam4140.stop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.appyvet.materialrangebar.RangeBar;
import com.dreamteam4140.stop.model.AppPreferences;

public class SettingsShakeActivity extends AppCompatActivity {

    Switch _switchShakeSett;

    TextView _minSeekBar;
    TextView _maxSeekBar;

    //RangeSlider Github
    //https://github.com/oli107/material-range-bar
    RangeBar _rangeBar;

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

        _minSeekBar = findViewById(R.id.minValueSeekBar);

        int min = AppPreferences.GetInstance(getApplicationContext()).getInt(AppPreferences.Key.SETTINGS_SHAKER_MIN_TIME, 0);
        _minSeekBar.setText(String.valueOf(min));

        _maxSeekBar = findViewById(R.id.maxValueSeekBar);
        int max = AppPreferences.GetInstance(getApplicationContext()).getInt(AppPreferences.Key.SETTINGS_SHAKER_MAX_TIME, 5);
        _maxSeekBar.setText(String.valueOf(max));


        _rangeBar = findViewById(R.id.rangebar);
        _rangeBar.setRangePinsByValue(min, max);

        _rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                _minSeekBar.setText(leftPinValue);
                _maxSeekBar.setText(rightPinValue);
            }

            @Override
            public void onTouchEnded(RangeBar rangeBar) {

            }

            @Override
            public void onTouchStarted(RangeBar rangeBar) {

            }
        });

    }

    public void saveShakeSetting(View view) {
        saveSettings();
        finish();
    }

    public void saveSettings() {
        AppPreferences.GetInstance(getApplicationContext()).put(AppPreferences.Key.SETTINGS_SHAKER_MIN_TIME, Integer.parseInt(_minSeekBar.getText().toString()));
        AppPreferences.GetInstance(getApplicationContext()).put(AppPreferences.Key.SETTINGS_SHAKER_MAX_TIME, Integer.parseInt(_maxSeekBar.getText().toString()));
    }

    @Override
    public void onBackPressed() {
        saveSettings();
        super.onBackPressed();
    }
}
