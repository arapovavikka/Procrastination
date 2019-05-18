package com.dreamteam4140.stop;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamteam4140.stop.activity.ExitActivity;
import com.dreamteam4140.stop.model.AppPreferences;
import com.dreamteam4140.stop.service.Timer;

import java.util.Calendar;

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
        load();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.turnOnAndOfServiceSwitch: {
                //some activity
                Log.i(TAG, "click on switch" + turnOnAndOfServiceSwitch.isChecked());
                controlService(turnOnAndOfServiceSwitch.isChecked());

                break;
            }
            case R.id.buttonStart: {
                //start timer
                //turn on "switch
                Log.i(TAG, "click on buttonStart");
                if (!timerStarted) {
                    if (!turnOnAndOfServiceSwitch.isChecked()) {
                        controlService(true);
                        turnOnAndOfServiceSwitch.setChecked(true);
                    }
                    buttonStart.setImageResource(R.mipmap.ic_pause_timer);
                    timer.start(timerTextView, 30, buttonStart);
                } else {

                    buttonStart.setImageResource(R.mipmap.ic_start_timer);
                    try {
                        timer.pause(timerTextView);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                timerStarted = !timerStarted;
                //navigateOnOverlay();
                break;
            }
            case R.id.sliderButton: {
                //some activity
                Intent navigationIntent = new Intent(this, SettingsSliderActivity.class);
                startActivity(navigationIntent);

                Log.i(TAG, "click on like1");
                break;
            }
            case R.id.shakeButton: {
                Intent navigationIntent = new Intent(this, SettingsShakeActivity.class);
                navigationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(navigationIntent);
                break;
            }
            case R.id.formulaButton: {
                //some activity
                Log.i(TAG, "click on formula");

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
                Intent myIntent = new Intent(MainActivity.this, TimerReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(getApplicationContext(), "Установлен таймер на 1 минуту!", Toast.LENGTH_LONG).show();
                ExitActivity.exitApplication(getApplicationContext());
                break;
            }
            case R.id.passwordButton: {
                Intent navigationIntent = new Intent(this, SettingsPasswordActivity.class);
                navigationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(navigationIntent);
                break;
            }
            case R.id.relaxTextView: {
                //some activity
                Intent navigationIntent = new Intent(this, SetTimerActivity.class);
                navigationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                AppPreferences.GetInstance(getApplicationContext()).put(AppPreferences.Key.NAVIGATE_TO_RELAX_TIMER, true);
                startActivity(navigationIntent);
                break;
            }
            case R.id.timeForUsingPhoneTextView: {
                //
                Intent navigationIntent = new Intent(this, SetTimerActivity.class);
                navigationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                AppPreferences.GetInstance(getApplicationContext()).put(AppPreferences.Key.NAVIGATE_TO_RELAX_TIMER, false);
                startActivity(navigationIntent);
                break;
            }
            default:
                break;
        }
    }


    @SuppressLint("WrongViewCast")
    private void initialize() {
        res = getResources();
        timer = new Timer();
        timerStarted = false;

        relaxTextView = findViewById(R.id.relaxTextView);
        timeForUsingPhoneTextView = findViewById(R.id.timeForUsingPhoneTextView);
        timerTextView = findViewById(R.id.timerTextView);
        turnOnAndOfServiceText = findViewById(R.id.turnOnAndOfServiceText);

        turnOnAndOfServiceSwitch = findViewById(R.id.turnOnAndOfServiceSwitch);

        buttonStart = findViewById(R.id.buttonStart);
        like1 = findViewById(R.id.sliderButton);
        formula = findViewById(R.id.formulaButton);
        like2 = findViewById(R.id.passwordButton);
        like3 = findViewById(R.id.shakeButton);


    }

    private void setOnClickListeners() {
        relaxTextView.setOnClickListener(this);
        timeForUsingPhoneTextView.setOnClickListener(this);

        like1.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
        formula.setOnClickListener(this);
        like2.setOnClickListener(this);
        like3.setOnClickListener(this);

        turnOnAndOfServiceSwitch.setOnClickListener(this);
    }

    private void controlService(boolean turnOnService) {
        if (turnOnService) {
            turnOnAndOfServiceText.setText(res.getText(R.string.turnOnService));
            buttonStart.setActivated(true);
        } else {
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

    private void save() {
        String[] time = timerTextView.getText().toString().split(":");
        int seconds = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
        Log.i(TAG, String.valueOf(seconds));
        AppPreferences.GetInstance(getApplicationContext()).put(AppPreferences.Key.TIMER_TIME, seconds);

    }


    private void load() {
        int second = AppPreferences.GetInstance(getApplicationContext()).getInt(AppPreferences.Key.TIMER_TIME);
        if (second != 0) {
            controlService(true);
            timerStarted = true;
            turnOnAndOfServiceSwitch.setChecked(true);
            buttonStart.setImageResource(R.mipmap.ic_pause_timer);
            timer.start(timerTextView, second, buttonStart);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        getTimerRelaxText();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTimerRelaxText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        save();
    }

    private void getTimerRelaxText()
    {
        int relaxMin = AppPreferences.GetInstance(getApplicationContext()).getInt(AppPreferences.Key.SETTINGS_RELAX_TIME_MIN, 0);
        int relaxHour = AppPreferences.GetInstance(getApplicationContext()).getInt(AppPreferences.Key.SETTINGS_RELAX_TIME_HOUR, 0);
        if (relaxHour != 0 && relaxMin != 0)
            relaxTextView.setText(relaxHour + "Ч " + relaxMin + " мин");
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
