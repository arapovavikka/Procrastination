package com.dreamteam4140.stop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OverlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
    }

    public void closeOverlay(View view)
    {
        finish();
    }
}
