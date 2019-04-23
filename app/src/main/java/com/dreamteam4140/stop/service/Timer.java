package com.dreamteam4140.stop.service;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;


import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class Timer {
    private static final String TAG = "Timer";
    private CountDownTimer countDownTimer;
    private int timeInSeconds;

    public void  start(final TextView textView, int seconds, final ImageButton imageButton){
        if(timeInSeconds!=0) seconds=timeInSeconds;
            countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeInSeconds = (int) millisUntilFinished / 1000;
                    textView.setText(getHoursAndMinutes(timeInSeconds));
                }

                @Override
                public void onFinish() {
                    //imageButton.setImageResource(R.mipmap.ic_start_timer);

                }
            }.start();
        }

    public void pause (final TextView textView) throws InterruptedException {
        if(countDownTimer!=null) {
            Log.i(TAG, String.valueOf(timeInSeconds));
            countDownTimer.cancel();
        }
    }
    public void cancle (final TextView textView) throws InterruptedException {
        if(countDownTimer!=null) {
            timeInSeconds=0;
            textView.setText("00:00");
            Log.i(TAG, String.valueOf(timeInSeconds));
            countDownTimer.cancel();
        }
    }


    @SuppressLint("DefaultLocale")
    private String getHoursAndMinutes(int seconds){
        return (String.format("%02d",seconds / 60)+":"
                +String.format("%02d",seconds % 60));

    }
}
