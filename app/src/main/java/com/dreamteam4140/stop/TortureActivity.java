package com.dreamteam4140.stop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.dreamteam4140.stop.fragment.FragmentPassword;
import com.dreamteam4140.stop.fragment.FragmentShake;
import com.dreamteam4140.stop.fragment.FragmentSlider;
import com.dreamteam4140.stop.model.AppPreferences;

public class TortureActivity extends AppCompatActivity {

    FragmentPassword _passwordFragment;
    FragmentSlider _sliderFragment;
    FragmentShake _shakeFragment;

    FragmentManager _fragmentManager;

    final static String TAG_PASSWORD = "FRAGMENT_PASSWORD";
    final static String TAG_SLIDER = "FRAGMENT_SLIDER";
    final static String TAG_SHAKER = "FRAGMENT_SHAKER";

    //параметры доступности экрана
    boolean _passwordEnabled;
    boolean _sliderEnabled;
    boolean _shakerEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torture);

        _fragmentManager = getSupportFragmentManager();
        _passwordFragment = new FragmentPassword();
        _shakeFragment = new FragmentShake();
        _sliderFragment = new FragmentSlider();


        _passwordEnabled = AppPreferences.GetInstance(getApplicationContext()).getBool(AppPreferences.Key.PASSWORD_ENABLED, false);
        _shakerEnabled = AppPreferences.GetInstance(getApplicationContext()).getBool(AppPreferences.Key.SHAKER_ENABLED, false);
        _sliderEnabled = AppPreferences.GetInstance(getApplicationContext()).getBool(AppPreferences.Key.SLIDER_ENABLED, false);
         if (!_passwordEnabled && !_shakerEnabled && !_sliderEnabled)
         {
             //по умолчанию экран слайдера
             FragmentTransaction transaction = _fragmentManager.beginTransaction();
             transaction.add(R.id.container, _sliderFragment, TAG_SLIDER);
             transaction.commit();
         }
         /*if(_passwordEnabled)
         {
             //по умолчанию экран слайдера
             FragmentTransaction transaction = _fragmentManager.beginTransaction();
             transaction.add(R.id.container, _passwordFragment, TAG_SLIDER);
             transaction.commit();
         }*/


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Do nothing or catch the keys you want to block
        return false;
    }
}
