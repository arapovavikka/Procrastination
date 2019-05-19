package com.dreamteam4140.stop;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamteam4140.stop.activity.ExitActivity;
import com.dreamteam4140.stop.model.AppPreferences;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class UnlockPasswordActivity extends AppCompatActivity implements View.OnClickListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_password);
        initialize();
        setOnClickListeners();

        _passwordSetting = AppPreferences.GetInstance(getApplicationContext()).getString(AppPreferences.Key.SETTINGS_PASSWORD_STR);
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
                _passwordEditText.setSelection(_passwordEditText.getText().length());
                if (_passwordEditText.equals(_passwordSetting))
                {
                    ExitActivity.exitApplication(getApplicationContext());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
    @SuppressLint("CutPasteId")
    private void initialize(){
        ArrayList<Integer> randomArrayList = getRandomArrayList(0,9);
        _clearButton = findViewById(R.id.buttonClear);

        button2 = findViewById(R.id.button2);
        button2.setText(String.valueOf(randomArrayList.get(0)));

        button3 = findViewById(R.id.button3);
        button3.setText(String.valueOf(randomArrayList.get(1)));

        textView4 = findViewById(R.id.textView4);
        textView4.setText(String.valueOf(randomArrayList.get(2)));

        textView5 = findViewById(R.id.textView5);
        textView5.setText(String.valueOf(randomArrayList.get(3)));

        textView6 = findViewById(R.id.textView6);
        textView6.setText(String.valueOf(randomArrayList.get(4)));

        textView7 = findViewById(R.id.textView7);
        textView7.setText(String.valueOf(randomArrayList.get(5)));

        textView8 = findViewById(R.id.textView8);
        textView8.setText(String.valueOf(randomArrayList.get(6)));

        textView9 = findViewById(R.id.textView9);
        textView9.setText(String.valueOf(randomArrayList.get(7)));

        textView10 = findViewById(R.id.textView10);
        textView10.setText(String.valueOf(randomArrayList.get(8)));

        _upperLeftButton = findViewById(R.id.upperLeftButton);
        _upperLeftButton.setText(String.valueOf(randomArrayList.get(9)));

        _passwordEditText = findViewById(R.id.passwordEditText);

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


        TextView clickedTextView  = findViewById(v.getId());
        _passwordEditText.setText(_passwordEditText.getText().toString()+
                clickedTextView.getText().toString());

    }
}
