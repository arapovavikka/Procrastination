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
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamteam4140.stop.activity.ExitActivity;
import com.dreamteam4140.stop.model.AppPreferences;
import com.dreamteam4140.stop.service.Timer;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "MainActivity";

    private TextView relaxTextView;
    private TextView timerTextView;
    private TextView turnOnAndOfServiceText;
    private TextView timeForUsingPhoneTextView;

    private ImageButton sliderButton;
    private ImageButton buttonStart;
    private ImageButton formulaButton;
    private ImageButton passwordButton;
    private ImageButton shakeButton;

    private SwitchCompat turnOnAndOfServiceSwitch;
    private Resources res;
    private Timer timer;
    private SharedPreferences sPref;
    private final String SAVED_TIME = "saved_time";

    private boolean timerStarted;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private String finishedTime;
    private AppPreferences appPreferences;
    private int seconds;
    private int secondsRelax;

    private enum TimerSTATUS {
        STARTING,
        PAUSE,
        CANCLE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setOnClickListeners();
        load();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonStart: {
                setTimerStatus(!timerStarted);
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
        Intent myIntent = new Intent(MainActivity.this, TimerReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        seconds = 0;
        appPreferences = AppPreferences.GetInstance(getApplicationContext());
        res = getResources();
        timer = new Timer(res);
        timerStarted = false;
        relaxTextView = findViewById(R.id.relaxTextView);
        timeForUsingPhoneTextView = findViewById(R.id.timeForUsingPhoneTextView);
        timerTextView = findViewById(R.id.timerTextView);
        turnOnAndOfServiceText = findViewById(R.id.turnOnAndOfServiceText);
        turnOnAndOfServiceSwitch = findViewById(R.id.turnOnAndOfServiceSwitch);
        buttonStart = findViewById(R.id.buttonStart);
        sliderButton = findViewById(R.id.sliderButton);
        formulaButton = findViewById(R.id.formulaButton);
        passwordButton = findViewById(R.id.passwordButton);
        shakeButton = findViewById(R.id.shakeButton);


    }

    private void setOnClickListeners() {
        relaxTextView.setOnClickListener(this);
        timeForUsingPhoneTextView.setOnClickListener(this);

        sliderButton.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
        formulaButton.setOnClickListener(this);
        passwordButton.setOnClickListener(this);
        shakeButton.setOnClickListener(this);
        turnOnAndOfServiceSwitch.setOnCheckedChangeListener(this);
    }

    private void save() {
        Log.i(TAG, "Stopped Timer: " + Calendar.getInstance().getTimeInMillis());

        appPreferences.put(AppPreferences.Key.TIME_OF_STOPPED, Calendar.getInstance().getTimeInMillis());
        if (timer.getTimeInSeconds() != 0) {
            appPreferences.put(AppPreferences.Key.TIMER_TIME, timer.getTimeInSeconds());
        } else {
            appPreferences.put(AppPreferences.Key.TIMER_TIME, timer.getSecondsByTimerTextView(timerTextView.getText().toString()));
        }
        Log.i(TAG, "seconds Saved " + appPreferences.getInt(AppPreferences.Key.TIMER_TIME));

        checkConditionOfTimer();

    }


    private void load() {
        Log.i(TAG, "Loading Time: " + Calendar.getInstance().getTime());
        timerStarted = appPreferences.getBool(AppPreferences.Key.IS_PLAY);

        timeForUsingPhoneTextView.setText(String.format(res.getString(R.string.timer),
                appPreferences.getInt(AppPreferences.Key.SETTINGS_WORK_TIME_HOUR, 0),
                appPreferences.getInt(AppPreferences.Key.SETTINGS_WORK_TIME_MIN, 0)));
        relaxTextView.setText(String.format(res.getString(R.string.timer),
                appPreferences.getInt(AppPreferences.Key.SETTINGS_RELAX_TIME_HOUR, 0),
                appPreferences.getInt(AppPreferences.Key.SETTINGS_RELAX_TIME_MIN, 0)));

        if (appPreferences.getBool(AppPreferences.Key.IS_CHANGE_TIME)) {
            int hours = appPreferences.getInt(AppPreferences.Key.SETTINGS_WORK_TIME_HOUR, 0);
            int minutes = appPreferences.getInt(AppPreferences.Key.SETTINGS_WORK_TIME_MIN, 0);
            int hoursRelax = appPreferences.getInt(AppPreferences.Key.SETTINGS_RELAX_TIME_HOUR, 0);
            int minutesRelax = appPreferences.getInt(AppPreferences.Key.SETTINGS_RELAX_TIME_MIN, 0);
            setServiceStatus(false);
            timerTextView.setText(String.format("%02d", hours) + ":"
                    + String.format("%02d", minutes) + ":" + String.format("%02d", 0));
            seconds = timer.getSecondsByTimerTextViewWithSeconds(timerTextView.getText().toString());

            secondsRelax = timer.getSecondsByTimerTextViewWithSeconds(String.format("%02d", hoursRelax) + ":"
                    + String.format("%02d", minutesRelax) + ":" + String.format("%02d", 0));
        } else {
            if (timerStarted) {
                seconds = Timer.getStringByCurrentTime(appPreferences.getLong(AppPreferences.Key.TIME_OF_STOPPED),
                        appPreferences.getInt(AppPreferences.Key.TIMER_TIME));
            } else {
                seconds = appPreferences.getInt(AppPreferences.Key.TIMER_TIME);
                timerTextView.setText(timer.getHoursMinutesAndSeconds(seconds));
            }
            setServiceStatus(appPreferences.getBool(AppPreferences.Key.TURN_ON_OF_SERVICE));
            if (seconds != 0) {
                setTimerStatus(timerStarted);
            } else {
                setTimerStatus(!timerStarted);
            }
        }
        appPreferences.put(AppPreferences.Key.IS_CHANGE_TIME, false);


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

    private void getTimerRelaxText() {
    }

    public void openSetTimer(View view) {
        Intent navigationIntent = new Intent(this, SetTimerActivity.class);
        startActivity(navigationIntent);
    }

    public void navigateToPassword(View view) {
        Intent navigationIntent = new Intent(this, SettingsPasswordActivity.class);
        startActivity(navigationIntent);
    }

    private void checkConditionOfTimer() {
        Log.i(TAG, "SAVE");
        Log.i(TAG, "turnOnAndOfServiceSwitch.isChecked()  " + turnOnAndOfServiceSwitch.isChecked());
        Log.i(TAG, "timerStarted  " + timerStarted);
        Log.i(TAG, "-------------");
        appPreferences.put(AppPreferences.Key.TURN_ON_OF_SERVICE, turnOnAndOfServiceSwitch.isChecked());
        if (timerStarted) {
            appPreferences.put(AppPreferences.Key.IS_PLAY, true);
        } else {
            appPreferences.put(AppPreferences.Key.IS_PLAY, false);
        }

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setServiceStatus(isChecked);
    }


    private void setTimerStatus(boolean startTimer) {

        Log.i(TAG, "click on buttonStart");

        Log.i(TAG, "Switch is checked" + turnOnAndOfServiceSwitch.isChecked() + "\n");
        Log.i(TAG, "--------------");
        if (turnOnAndOfServiceSwitch.isChecked()) {
            if (startTimer) {
                setTimerStatus(TimerSTATUS.STARTING);
                buttonStart.setImageResource(R.mipmap.ic_pause_timer);
            } else {
                setTimerStatus(TimerSTATUS.PAUSE);
                buttonStart.setImageResource(R.mipmap.ic_start_timer);
            }
            timerStarted = startTimer;
        }

        Log.i(TAG, "timerStarted  " + timerStarted);

    }

    private void setServiceStatus(boolean isChecked) {
        Log.i(TAG, "Отслеживание переключения: " + (isChecked ? "on" : "off"));
        turnOnAndOfServiceSwitch.setChecked(isChecked);
        if (isChecked) {
            turnOnAndOfServiceText.setText(res.getText(R.string.turnOnService));
        } else {
            turnOnAndOfServiceText.setText(res.getText(R.string.turnOfService));
            setTimerStatus(TimerSTATUS.CANCLE);
            timerStarted = false;
            seconds = 0;
            buttonStart.setImageResource(R.mipmap.ic_start_timer);
        }
    }


    private void setTimerStatus(TimerSTATUS status) {
        switch (status) {
            case STARTING: {
                Log.d(TAG, "seconds Before overlay: " + seconds);
                setOverlayTimer(false,
                       seconds, true);
                timer.start(timerTextView, seconds, buttonStart);
                Log.d(TAG, "Turn On Timer ");

                break;
            }
            case PAUSE: {
                try {
                    seconds = timer.getSecondsByTimerTextViewWithSeconds(timerTextView.getText().toString());
                    timer.pause(timerTextView);
                    pendingIntent.cancel();
                } catch (InterruptedException ignored) {

                }
                Log.d(TAG, "Pause Timer ");
                break;
            }
            case CANCLE: {
                Log.d(TAG, "Cancle Timer ");
                try {
                    seconds=0;
                    timer.cancle(timerTextView);
                    pendingIntent.cancel();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }



    private void setOverlayTimer(boolean isCloseApplication, int timeForOverlayTimer, boolean isOutPutAllowed) {
        Intent myIntent = new Intent(MainActivity.this, TimerReceiver.class);
        myIntent.putExtra("secondsRelax", secondsRelax);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + timeForOverlayTimer);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        if (isOutPutAllowed) {

            Toast.makeText(getApplicationContext(), "Таймер прозвонит через " + timer.getHoursMinutesAndSeconds(timeForOverlayTimer) + "(чч:мм:cc)!", Toast.LENGTH_LONG).show();

        }
        if (isCloseApplication) {
            save();
            ExitActivity.exitApplication(getApplicationContext());
        }
    }

    private void setOverlayTimer(boolean isCloseApplication, int timeForOverlayTimer, String outPutString, boolean isOutPutAllowed) {
        Intent myIntent = new Intent(MainActivity.this, TimerReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + timeForOverlayTimer);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        if (isOutPutAllowed) {
            if (outPutString != null) {
                Toast.makeText(getApplicationContext(), "Таймер прозвонит через: " + outPutString + " (чч:мм)", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "Установлен таймер на " + timeForOverlayTimer + "секунд!", Toast.LENGTH_LONG).show();
            }
        }
        if (isCloseApplication) {
            save();
            ExitActivity.exitApplication(getApplicationContext());
        }
    }
}