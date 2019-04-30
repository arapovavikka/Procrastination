package com.dreamteam4140.stop;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.CompoundButton;
import android.support.annotation.StringRes;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;
import android.content.SharedPreferences;
import android.content.res.Resources;

import java.util.Calendar;
import com.dreamteam4140.stop.service.Timer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private TextView relaxTextView;
    private TextView timerTextView;
    private TextView turnOnAndOfServiceText;
    private TextView timeForUsingPhoneTextView;

    private ImageButton like1;
    private ImageButton buttonStart;
    private ImageButton formula;
    private ImageButton like2;
    private ImageButton like3;
    private ImageButton like4;

    private SwitchCompat turnOnAndOfServiceSwitch;
    private Resources res;
    private Timer timer;
    private SharedPreferences sPref;
    private final String SAVED_TIME = "saved_time";

    private boolean timerStarted;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setOnClickListeners();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        loadText();
    }

    private void navigateOnOverlay() {
        Intent navigationIntent = new Intent(this, InstructionActivity.class);
        startActivity(navigationIntent);
        finish();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.turnOnAndOfServiceSwitch: {
                //some activity
                Log.i(TAG, "click on switch"+turnOnAndOfServiceSwitch.isChecked());
                controlService(turnOnAndOfServiceSwitch.isChecked());

                break;
            }
            case R.id.buttonStart: {
                //start timer
                //turn on "switch
                Log.i(TAG, "click on buttonStart");
                if(!timerStarted){
                    if(!turnOnAndOfServiceSwitch.isChecked()){
                        controlService(true);
                        turnOnAndOfServiceSwitch.setChecked(true);
                    }
                    buttonStart.setImageResource(R.mipmap.ic_pause_timer);
                    timer.start(timerTextView,30,buttonStart);
                }
                else {

                    buttonStart.setImageResource(R.mipmap.ic_start_timer);
                    try {
                        timer.pause(timerTextView);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                timerStarted=!timerStarted;
                //navigateOnOverlay();
                break;
            }
            case R.id.like1: {
                //some activity
                Intent navigationIntent = new Intent(this, SettingsPasswordActivity.class);
                startActivity(navigationIntent);

                Log.i(TAG, "click on like1");
                break;
            }
            case R.id.like2: {
                //some activity
                Log.i(TAG, "click on like2");
                break;
            }
            case R.id.formula: {
                //some activity
                Log.i(TAG, "click on formula");

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
                Intent myIntent = new Intent(MainActivity.this, TimerReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(getApplicationContext(), "Установлен таймер на 1 минуту!", Toast.LENGTH_LONG).show();
                finish();
                break;
            }
            case R.id.like3: {
                //some activity
                Log.i(TAG, "click on like3");
                break;
            }
            case R.id.like4: {
                //some activity
                Log.i(TAG, "click on like4");
                break;
            }
            case R.id.timeForUsingPhoneTextView: {
                //
                Intent navigationIntent = new Intent(this, SetTimerActivity.class);
                startActivity(navigationIntent);
                break;
            }
            default: break;
        }
    }

    @SuppressLint("WrongViewCast")
    private void initialize() {
        res = getResources();
        timer =new Timer();
        timerStarted = false;

        relaxTextView = (TextView) findViewById(R.id.relaxTextView);
        timeForUsingPhoneTextView = (TextView) findViewById(R.id.timeForUsingPhoneTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        turnOnAndOfServiceText = (TextView) findViewById(R.id.turnOnAndOfServiceText);

        turnOnAndOfServiceSwitch = (SwitchCompat) findViewById(R.id.turnOnAndOfServiceSwitch);

        buttonStart = (ImageButton) findViewById(R.id.buttonStart);
        like1 = (ImageButton) findViewById(R.id.like1);
        formula = (ImageButton) findViewById(R.id.formula);
        like2 = (ImageButton) findViewById(R.id.like2);
        like3 = (ImageButton) findViewById(R.id.like3);
        like4 = (ImageButton) findViewById(R.id.like4);


    }
    private void setOnClickListeners(){
        relaxTextView.setOnClickListener(this);
        timeForUsingPhoneTextView.setOnClickListener(this);

        like1.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
        formula.setOnClickListener(this);
        like2.setOnClickListener(this);
        like3.setOnClickListener(this);
        like4.setOnClickListener(this);

        turnOnAndOfServiceSwitch.setOnClickListener(this);
    }

    private void controlService(boolean turnOnService){
        if(turnOnService){
            turnOnAndOfServiceText.setText(res.getText(R.string.turnOnService));
            buttonStart.setActivated(true);
        }
        else {
            turnOnAndOfServiceText
                    .setText(res.getText(R.string.turnOfService));
            try {
                timer.cancle(timerTextView);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            buttonStart.setImageResource(R.mipmap.ic_start_timer);
        }


    }

    private void saveText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        String [] time =timerTextView.getText().toString().split(":");
        int seconds = Integer.parseInt(time[0])*60+Integer.parseInt(time[1]);
        Log.i(TAG, String.valueOf(seconds));
        ed.putInt(SAVED_TIME, seconds);
        ed.commit();



    }


    private void loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        int second = sPref.getInt(SAVED_TIME, 0);
        if(second!=0){
            controlService(true);
            timerStarted=true;
            turnOnAndOfServiceSwitch.setChecked(true);
            buttonStart.setImageResource(R.mipmap.ic_pause_timer);
            timer.start(timerTextView,second,buttonStart);
        }
        else {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }

    public void openSetTimer(View view) {
        Intent navigationIntent = new Intent(this, SetTimerActivity.class);
        startActivity(navigationIntent);
    }

    public void navigateToPassword(View view) {
        Intent navigationIntent = new Intent(this, SettingsPasswordActivity.class);
        startActivity(navigationIntent);
    }

}







/*{

    private PendingIntent pendingIntent;
    AlarmManager alarmManager;

    SwitchCompat serviceSwitch;
    TextView serviceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        serviceTextView = findViewById(R.id.turnOnOfServiceText);

        serviceSwitch = findViewById(R.id.turnOnOfServiceSwitch);
        serviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    serviceTextView.setText(getString(R.string.service_on_text));
                    ActivateTimerClick();
                }
                else {
                    serviceTextView.setText(getString(R.string.service_off_text));
                }
            }
        });
    }

    public void ActivateTimerClick()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
        Intent myIntent = new Intent(MainActivity.this, TimerReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "1 min timer set! Closing...", Toast.LENGTH_LONG).show();
        finish();
    }


    public void openSetTimer(View view) {
        Intent navigationIntent = new Intent(this, SetTimerActivity.class);
        startActivity(navigationIntent);
    }

    public void navigateToPassword(View view) {
        Intent navigationIntent = new Intent(this, SettingsPasswordActivity.class);
        startActivity(navigationIntent);
    }



}
*/