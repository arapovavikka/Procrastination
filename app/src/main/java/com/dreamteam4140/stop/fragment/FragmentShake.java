package com.dreamteam4140.stop.fragment;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamteam4140.stop.R;
import com.dreamteam4140.stop.model.AppPreferences;
import com.squareup.seismic.ShakeDetector;




public class FragmentShake extends Fragment implements ShakeDetector.Listener{

    TextView _shakeTimer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_shake, null);
        _shakeTimer = view.findViewById(R.id.timerTextView);

        //для шейкера берем максимальное значение
        int maxTime = AppPreferences.GetInstance(getContext()).getInt(AppPreferences.Key.SETTINGS_SHAKER_MAX_TIME, 0);
        _shakeTimer.setText(String.valueOf(maxTime));

        SensorManager sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);

        return view;
    }

    @Override public void hearShake() {
        Toast.makeText(getContext(), "Don't shake me, bro!", Toast.LENGTH_SHORT).show();
    }
}
