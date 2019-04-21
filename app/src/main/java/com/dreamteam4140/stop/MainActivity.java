package com.dreamteam4140.stop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSetTimer(View view) {
        Intent navigationIntent = new Intent(this, SetTimerActivity.class);
        startActivity(navigationIntent);
    }

    public void navigateOnOverlay(View view) {
        Intent navigationIntent = new Intent(this, InstructionActivity.class);
        startActivity(navigationIntent);
        finish();
    }
}
