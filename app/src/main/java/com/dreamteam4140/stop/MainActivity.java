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
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
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
        appPreferences.put(AppPreferences.Key.TIMER_FINISHED_TIME, timer.getFinishedTimer());
        appPreferences.put(AppPreferences.Key.TIMER_TIME, seconds);
        checkConditionOfTimer();

    }


    private void load() {
        timerStarted=appPreferences.getBool(AppPreferences.Key.IS_PLAY);
        /*Log.i(TAG, "LOAD");
        Log.i(TAG, "turnOnAndOfServiceSwitch.isChecked()  "+appPreferences.getBool(AppPreferences.Key.TURN_ON_OF_SERVICE));
        Log.i(TAG, "timerStarted  "+appPreferences.getBool(AppPreferences.Key.IS_PLAY));
        Log.i(TAG, "-------------");*/

        timeForUsingPhoneTextView.setText(String.format(res.getString(R.string.timer),
                appPreferences.getInt(AppPreferences.Key.SETTINGS_WORK_TIME_HOUR,0),
                appPreferences.getInt(AppPreferences.Key.SETTINGS_WORK_TIME_MIN,0)));
        relaxTextView.setText(String.format(res.getString(R.string.timer),
                appPreferences.getInt(AppPreferences.Key.SETTINGS_RELAX_TIME_HOUR,0),
                appPreferences.getInt(AppPreferences.Key.SETTINGS_RELAX_TIME_MIN,0)));

        if(appPreferences.getBool(AppPreferences.Key.IS_CHANGE_TIME)){
            int hours = appPreferences.getInt(AppPreferences.Key.SETTINGS_WORK_TIME_HOUR,0);
            int minutes = appPreferences.getInt(AppPreferences.Key.SETTINGS_WORK_TIME_MIN,0);
            setServiceStatus(false);
            timerTextView.setText(String.format("%02d", hours) + ":"
                    + String.format("%02d", minutes));
            seconds=Timer.getSecondsByTimerTextView(timerTextView.getText().toString());
        }
        else {
            seconds=appPreferences.getInt(AppPreferences.Key.TIMER_TIME,0);
            timerTextView.setText(Timer.getByTimerTextViewSeconds(seconds));
            setServiceStatus(appPreferences.getBool(AppPreferences.Key.TURN_ON_OF_SERVICE));
            setTimerStatus(timerStarted);
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
        Log.i(TAG, "turnOnAndOfServiceSwitch.isChecked()  "+turnOnAndOfServiceSwitch.isChecked());
        Log.i(TAG, "timerStarted  "+timerStarted);
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
        if(turnOnAndOfServiceSwitch.isChecked()) {
            if (startTimer) {
                setTimerStatus(TimerSTATUS.STARTING);
                buttonStart.setImageResource(R.mipmap.ic_pause_timer);
            } else {
                setTimerStatus(TimerSTATUS.PAUSE);
                buttonStart.setImageResource(R.mipmap.ic_start_timer);
            }
            timerStarted = startTimer;
        }

        Log.i(TAG, "timerStarted  "+timerStarted);
                /*if (timerStarted) {
                    Log.d(TAG, "Change button image");
                    buttonStart.setImageResource(R.mipmap.ic_pause_timer);
                    timer.start(timerTextView, seconds, buttonStart);
                } else {
                    Log.d(TAG, "Change button image");
                    buttonStart.setImageResource(R.mipmap.ic_start_timer);
                    try {
                        timer.pause(timerTextView);
                    } catch (InterruptedException ignored) {

                    }
                }
                timerStarted = !timerStarted;
                if (timerStarted) {
                    Log.d(TAG, "Time starting");
                } else {
                    Log.d(TAG, "Time stopped");
                }*/
        //navigateOnOverlay();

    }
    private void  setServiceStatus(boolean isChecked){
        Log.i(TAG, "Отслеживание переключения: " + (isChecked ? "on" : "off"));
       turnOnAndOfServiceSwitch.setChecked(isChecked);
        if (isChecked) {
            turnOnAndOfServiceText.setText(res.getText(R.string.turnOnService));
        } else {
            turnOnAndOfServiceText.setText(res.getText(R.string.turnOfService));
            setTimerStatus(TimerSTATUS.CANCLE);
            timerStarted=false;
            seconds=0;
            buttonStart.setImageResource(R.mipmap.ic_start_timer);
        }
    }


private void setTimerStatus (TimerSTATUS status){
        switch (status){
            case STARTING:{
                Log.d(TAG, "Seconds"+seconds );
                timer.start(timerTextView, seconds, buttonStart);
                Log.d(TAG, "Turn On Timer " );
                break;
            }
            case PAUSE:{
                try {
                    timer.pause(timerTextView);
                } catch (InterruptedException ignored) {

                }
                Log.d(TAG, "Pause Timer " );
                break;
            }
            case CANCLE:{
                Log.d(TAG, "Cancle Timer " );
                try {
                    timer.cancle(timerTextView);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }

        }


}
}

/*  private void controlService(boolean turnOnService, boolean timerIsStarted) {
        buttonStart.setImageResource(R.mipmap.ic_pause_timer);
        Log.d(TAG, "turnOnService " + turnOnService);
        if (turnOnService) {
            turnOnAndOfServiceText.setText(res.getText(R.string.turnOnService));
            turnOnAndOfServiceSwitch.setActivated(true);
            buttonStart.setActivated(true);


        } else {
            turnOnAndOfServiceText
                    .setText(res.getText(R.string.turnOfService));
            turnOnAndOfServiceSwitch.setActivated(false);
            buttonStart.setActivated(false);
            try {
                timer.cancle(timerTextView);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "Change button image");
            buttonStart.setImageResource(R.mipmap.ic_start_timer);
        }


    }*/


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
*/
