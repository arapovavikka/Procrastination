package com.dreamteam4140.stop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SetTimerActivity extends AppCompatActivity {

    private int MAX_HOUR = 23;
    private int MAX_MINUTES = 59;

    private TextView _hourTextView;
    private TextView _minutesTextView;

    private enum Operation {
        Plus,
        Minus
    }

    private enum TimeType {
        MINUTE,
        HOUR
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);

        _hourTextView = findViewById(R.id.hourTextView);
        _minutesTextView = findViewById(R.id.minutesTextView);
    }

    public void closeLayout(View view)
    {
        finish();
    }

    public void plusHourClicked(View view)
    {
        String hourText = _hourTextView.getText().toString();
        int number;
        try {
            number = Integer.parseInt(hourText);
            number = CheckConstraint(number, MAX_HOUR, Operation.Plus, TimeType.HOUR);
        }
        catch (NumberFormatException e)
        {
            number = 0;
        }

        _hourTextView.setText(Integer.toString(number));
    }

    public int CheckConstraint(int value, int maxValue, Operation sign, TimeType timeType)
    {
        switch(sign){
            case Plus: {
                if (value == maxValue) {
                    value = 0;
                    if (timeType == timeType.MINUTE)
                    {
                        plusHourClicked(_hourTextView);
                    }
                }
                else
                    value++;
                break;
            }
            case Minus: {
                if (value == 0) {
                    value = maxValue;
                    if (timeType == timeType.MINUTE)
                    {
                        minusHourClicked(_hourTextView);
                    }
                }
                else
                    value--;
                break;
            }
        }
        return value;
    }

    public void minusHourClicked(View view)
    {
        String hourText = _hourTextView.getText().toString();
        int number;
        try {
            number = Integer.parseInt(hourText);
            number = CheckConstraint(number, MAX_HOUR, Operation.Minus, TimeType.HOUR);
        }
        catch (NumberFormatException e)
        {
            number = 0;
        }

        _hourTextView.setText(Integer.toString(number));
    }

    public void minusMinClicked(View view)
    {
        String minuteText = _minutesTextView.getText().toString();
        int number;
        try {
            number = Integer.parseInt(minuteText);
            number = CheckConstraint(number, MAX_MINUTES, Operation.Minus, TimeType.MINUTE);
        }
        catch (NumberFormatException e)
        {
            number = 0;
        }

        _minutesTextView.setText(Integer.toString(number));
    }

    public void plusMinClicked(View view)
    {
        String minuteText = _minutesTextView.getText().toString();
        int number;
        try {
            number = Integer.parseInt(minuteText);
            number = CheckConstraint(number, MAX_MINUTES, Operation.Plus, TimeType.MINUTE);
        }
        catch (NumberFormatException e)
        {
            number = 0;
        }

        _minutesTextView.setText(Integer.toString(number));
    }

    public void saveTimer(View view)
    {

    }
}
