package com.dreamteam4140.stop.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appyvet.materialrangebar.RangeBar;
import com.dreamteam4140.stop.R;
import com.dreamteam4140.stop.activity.ExitActivity;

public class FragmentSlider extends Fragment {

    RangeBar _slider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_slider, null);

        _slider = view.findViewById(R.id.sliderRangeBar);
        _slider.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                if (rightPinValue.equals("5"))
                {
                    //вот тут устанавливаем таймер еще раз!
                    ExitActivity.exitApplication(getActivity());
                }
            }

            @Override
            public void onTouchEnded(RangeBar rangeBar) {
                //Toast.makeText(getContext(), rangeBar.getLeft() + " " + rangeBar.getRight(), Toast.LENGTH_LONG);
            }

            @Override
            public void onTouchStarted(RangeBar rangeBar) {

            }
        });

        return view;
    }
}
