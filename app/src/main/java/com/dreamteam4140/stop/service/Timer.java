package com.dreamteam4140.stop.service;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dreamteam4140.stop.R;

import java.util.Calendar;
import java.util.Date;

public class Timer {
    private static final String TAG = "Timer";
    private CountDownTimer countDownTimer;

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    private int timeInSeconds;
    private Resources res;

    public Timer(Resources res) {
        this.res = res;
    }


    public void start(final TextView textView, int seconds, final ImageButton imageButton) {
        if (timeInSeconds != 0) seconds = timeInSeconds;
        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeInSeconds = (int) millisUntilFinished / 1000;
                textView.setText(getHoursAndMinutes(timeInSeconds));
                Log.i(TAG, "Seconds:"+timeInSeconds);
            }

            @Override
            public void onFinish() {
                imageButton.setImageResource(R.mipmap.ic_start_timer);

            }
        }.start();
    }

    public void start(final TextView textView, int seconds) {
        if (timeInSeconds != 0) seconds = timeInSeconds;
        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeInSeconds = (int) millisUntilFinished / 1000;
                textView.setText(getHoursAndMinutes(timeInSeconds));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void pause(final TextView textView) throws InterruptedException {
        if (countDownTimer != null) {
            Log.i(TAG, String.valueOf(timeInSeconds));
            countDownTimer.cancel();

        }
    }

    public void cancle(final TextView textView) throws InterruptedException {
        if (countDownTimer != null) {
            timeInSeconds = 0;
            textView.setText("00:00");
            Log.i(TAG, String.valueOf(timeInSeconds));
            countDownTimer.cancel();
        }
    }


    @SuppressLint("DefaultLocale")
    public String getHoursAndMinutes(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds - hours * 3600) / 60;
        return (String.format("%02d", hours) + ":"
                + String.format("%02d", minutes));

    }


    public static  int getSecondsByHourAndMinutes(int hour,int minutes){
        return (hour*3600+minutes*60);

    }
    public  static int  getSecondsByTimerTextView(String timerTextViewText){
        String [ ] hourAndMinutesArray=timerTextViewText.split(":");


        return (getSecondsByHourAndMinutes(Integer.parseInt(hourAndMinutesArray[0]),Integer.parseInt(hourAndMinutesArray[1])));
    }
    public static String  getByTimerTextViewSeconds(int seconds){
        int hours =seconds/3600;
        int minutes =(seconds-hours*3600)/60;

        return String.format("%02d", hours) + ":"
                + String.format("%02d", minutes);
    }


    public static   int  getStringByCurrentTime(Long time,int seconds){
        Calendar stoppedTime = Calendar.getInstance();
        stoppedTime.setTimeInMillis(time);
        stoppedTime.set(Calendar.SECOND,stoppedTime.get(Calendar.SECOND)+seconds);
        Calendar currentTime = Calendar.getInstance();
        if(currentTime.after(stoppedTime)){
            return 0;
        }
        return subtractTwoDate(stoppedTime,currentTime);



    }
    public  String getTimerStringBySeconds(int seconds) {
        //draft is: 00 Ч 00 мин
        int hours=seconds/3600;
        int minutes=(seconds-hours*3600)/60;
        return String.format(res.getString(R.string.timer), hours,minutes);
    }

    public static int subtractTwoDate(Calendar minuend,Calendar difference){
        int hours;
        if(difference.get(Calendar.DAY_OF_MONTH)<minuend.get(Calendar.DAY_OF_MONTH)){
            hours=minuend.get(Calendar.HOUR)+24-difference.get(Calendar.HOUR);
            System.out.println("true");
        }
        else {
            hours=minuend.get(Calendar.HOUR)-difference.get(Calendar.HOUR);
        }
        int minutes=minuend.get(Calendar.MINUTE)-difference.get(Calendar.MINUTE);
        int seconds=minuend.get(Calendar.SECOND)-difference.get(Calendar.SECOND);
        return (hours*3600+minutes*60+seconds);
    }
}
