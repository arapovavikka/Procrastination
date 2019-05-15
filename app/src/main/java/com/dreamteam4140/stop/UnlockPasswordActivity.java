package com.dreamteam4140.stop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UnlockPasswordActivity extends AppCompatActivity {

    private EditText _passwordEditText;
    private Button _clearButton;

    private Button _upperLeftButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_password);

        _clearButton = findViewById(R.id.buttonClear);

        _upperLeftButton = findViewById(R.id.upperLeftButton);

        _passwordEditText = findViewById(R.id.passwordEditText);
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
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        _upperLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _passwordEditText.setText(_passwordEditText.getText().toString() + _upperLeftButton.getText());
            }
        });

        _clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _passwordEditText.setText("");
                _clearButton.setVisibility(View.GONE);
            }
        });
    }
}
