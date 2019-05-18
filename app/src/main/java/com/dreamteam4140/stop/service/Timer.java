package com.dreamteam4140.stop.service;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dreamteam4140.stop.R;

import java.util.Calendar;

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

    public static int getSecondsByTimerString(String textViewString) {
        //draft is: 00 Ч 00 мин
        textViewString = textViewString.replaceAll("\\s+", "");
        int hoursIndex = textViewString.indexOf("Ч");
        return (Integer.parseInt(textViewString.substring(0, hoursIndex)) * 3600
                + Integer.parseInt(textViewString.substring(hoursIndex + 1, hoursIndex + 3)) * 60);
    }
    public static int getSecondsByHourAndMinutes(int hour,int minutes){
        return (hour*3600+minutes*60);

    }
    public static int  getSecondsByTimerTextView(String timerTextViewText){
        Log.i(TAG, "timerTextViewText"+timerTextViewText);
        String [ ] hourAndMinutesArray=timerTextViewText.split(":");

        return getSecondsByHourAndMinutes(Integer.parseInt(hourAndMinutesArray[0]),
                +Integer.parseInt(hourAndMinutesArray[1]));
    }
    public static String  getByTimerTextViewSeconds(int seconds){
        Log.i(TAG, "seconds"+seconds);
        int hours =seconds/3600;
        int minutes =(seconds-hours*3600)/60;

        return String.format("%02d", hours) + ":"
                + String.format("%02d", minutes);
    }

    public String getFinishedTimer() {
        Calendar cal = Calendar.getInstance();
        Log.i(TAG, "Need to change time");
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds - hours * 3600) / 60;
        int second = timeInSeconds - hours * 3600 - minutes * 60;
        cal.add(Calendar.HOUR, hours);
        cal.add(Calendar.MINUTE, minutes);
        cal.add(Calendar.SECOND, second);
        return (cal.get(Calendar.HOUR) + ":" +
                cal.get(Calendar.MINUTE) + ":" +
                cal.get(Calendar.SECOND));
    }

    public static int getRemainingTime(String finishedTime) {
        if(finishedTime.equals("")) return 0;
        String[] finishedTimeArray = finishedTime.split(":");
        int finishedSeconds;
        int currentSecond;
        Calendar cal = Calendar.getInstance();
        if (Integer.parseInt(finishedTimeArray[0]) < cal.get(Calendar.HOUR)) {
            finishedSeconds = (Integer.parseInt(finishedTimeArray[0])+24) * 3600 +
                    Integer.parseInt(finishedTimeArray[1]) * 60+Integer.parseInt(finishedTimeArray[2]);
        } else {
            finishedSeconds = Integer.parseInt(finishedTimeArray[0]) * 3600 +
                    Integer.parseInt(finishedTimeArray[1]) * 60+Integer.parseInt(finishedTimeArray[2]);
        }
        System.out.println(finishedSeconds);
        if(cal.get(Calendar.AM_PM)==Calendar.PM){
            currentSecond = (cal.get(Calendar.HOUR)+12) * 3600 +
                    cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND);
        }
        else {
            currentSecond = (cal.get(Calendar.HOUR)) * 3600 +
                    cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND);
        }


        System.out.println((cal.getTimeInMillis())/3600);
        System.out.println(currentSecond);
        return (finishedSeconds-currentSecond);
    }
    public  String getTimerStringBySeconds(int seconds) {
        //draft is: 00 Ч 00 мин
        int hours=seconds/3600;
        int minutes=(seconds-hours*3600)/60;
        return String.format(res.getString(R.string.timer), hours,minutes);
    }
}
