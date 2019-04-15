package com.dreamteam4140.stop;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

public class InstructionActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void ActivateTimerClick(android.view.View view)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 2);
        Intent myIntent = new Intent(InstructionActivity.this, TimerReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(InstructionActivity.this, 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "2 min timer set! Closing...", Toast.LENGTH_LONG).show();
        System.exit(1);
    }

}
