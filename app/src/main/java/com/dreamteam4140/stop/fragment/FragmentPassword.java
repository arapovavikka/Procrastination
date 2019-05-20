package com.dreamteam4140.stop.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamteam4140.stop.R;
import com.dreamteam4140.stop.activity.ExitActivity;
import com.dreamteam4140.stop.model.AppPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FragmentPassword extends Fragment implements View.OnClickListener {




    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_unlock_password, null);

        return view;
    }*/



    private EditText _passwordEditText;
    private Button _clearButton;
    private Button _upperLeftButton;

    private HashMap<TextView,Integer> viewToInteger;

    private Button button2;
    private Button button3;

    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
    private TextView textView10;

    private String _passwordSetting;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_unlock_password, null);
        initialize();
        setOnClickListeners();

        _passwordSetting = AppPreferences.GetInstance(getContext()).getString(AppPreferences.Key.SETTINGS_PASSWORD_STR);
        _passwordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!s.equals("")) {
                    _clearButton.setVisibility(View.VISIBLE);
                } else {
                    _clearButton.setVisibility(View.GONE);
                }
                String editText = String.valueOf(_passwordEditText.getText());
                if (String.valueOf(_passwordEditText.getText()).equals(_passwordSetting)) {
                    //вот сюда перед выходом можно добавить задание таймера
                    ExitActivity.exitApplication(getContext());
                }
                _passwordEditText.setSelection(_passwordEditText.getText().length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    @SuppressLint("CutPasteId")
    private void initialize(){
        ArrayList<Integer> randomArrayList = getRandomArrayList(0,9);
        _clearButton = view.findViewById(R.id.buttonClear);

        button2 = view.findViewById(R.id.textView29);
        button2.setText(String.valueOf(randomArrayList.get(0)));

        button3 = view.findViewById(R.id.textView30);
        button3.setText(String.valueOf(randomArrayList.get(1)));

        textView4 = view.findViewById(R.id.textView35);
        textView4.setText(String.valueOf(randomArrayList.get(2)));

        textView5 = view.findViewById(R.id.textView36);
        textView5.setText(String.valueOf(randomArrayList.get(3)));

        textView6 = view.findViewById(R.id.textView37);
        textView6.setText(String.valueOf(randomArrayList.get(4)));

        textView7 = view.findViewById(R.id.textView38);
        textView7.setText(String.valueOf(randomArrayList.get(5)));

        textView8 = view.findViewById(R.id.textView39);
        textView8.setText(String.valueOf(randomArrayList.get(6)));

        textView9 = view.findViewById(R.id.textView40);
        textView9.setText(String.valueOf(randomArrayList.get(7)));

        textView10 = view.findViewById(R.id.textView45);
        textView10.setText(String.valueOf(randomArrayList.get(8)));

        _upperLeftButton = view.findViewById(R.id.upperLeftButton);
        _upperLeftButton.setText(String.valueOf(randomArrayList.get(9)));

        _passwordEditText = view.findViewById(R.id.passwordEditText);

    }

    private ArrayList<Integer> getRandomArrayList (int start,int end ){

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = start; i <= end; i++) {

            arrayList.add(i);
        }

        Collections.shuffle(arrayList);
        return arrayList;
    }
    private void setOnClickListeners() {
        _upperLeftButton.setOnClickListener(this);
        button2.setOnClickListener(this);

        button3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        textView6.setOnClickListener(this);
        textView7.setOnClickListener(this);
        textView8.setOnClickListener(this);
        textView9.setOnClickListener(this);
        textView10.setOnClickListener(this);
        _clearButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonClear){
            _passwordEditText.setText("");
            _clearButton.setVisibility(View.GONE);
        }


        TextView clickedTextView  = view.findViewById(v.getId());
        _passwordEditText.setText(_passwordEditText.getText().toString()+
                clickedTextView.getText().toString());

    }

}
