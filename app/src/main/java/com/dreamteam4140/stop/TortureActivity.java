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

public class TortureActivity extends AppCompatActivity {

    FragmentPassword _passwordFragment;
    FragmentSlider _sliderFragment;
    FragmentShake _shakeFragment;

    FragmentManager _fragmentManager;

    final static String TAG_PASSWORD = "FRAGMENT_PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torture);

        _fragmentManager = getSupportFragmentManager();
        _passwordFragment = new FragmentPassword();
        _shakeFragment = new FragmentShake();
        _sliderFragment = new FragmentSlider();

        //при первом запуске
        FragmentTransaction transaction = _fragmentManager.beginTransaction();
        transaction.add(R.id.container, _sliderFragment, TAG_PASSWORD);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Do nothing or catch the keys you want to block
        return false;
    }
}
