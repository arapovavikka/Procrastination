package com.dreamteam4140.stop;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.dreamteam4140.stop.model.AppPreferences;

public class SettingsPasswordActivity extends Activity {

    EditText passwordEditText;
    Switch passwordSettingsSwitch;
    private static final int PASS_LEN = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_password);

        passwordEditText = findViewById(R.id.passwordEditText);
        passwordSettingsSwitch = findViewById(R.id.switchPassword);
        passwordSettingsSwitch.setChecked(AppPreferences.GetInstance(getApplicationContext()).getBool(AppPreferences.Key.PASSWORD_ENABLED, false));

        passwordSettingsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppPreferences.GetInstance(getApplicationContext()).put(AppPreferences.Key.PASSWORD_ENABLED, b);
                if (b) {
                    //do smth on switch On
                }
                else {
                    //do smth  on switch Off
                    //finish();
                }
            }
        });

    }



    public void savePassword(View view)
    {
        String password = passwordEditText.getText().toString();
        if (!password.isEmpty())
        {
            if (password.length() >= PASS_LEN) {
                AppPreferences.GetInstance(getApplicationContext()).put(AppPreferences.Key.SETTINGS_PASSWORD_STR, password);
                finish();
                return;
            }
        }
        Toast.makeText(getApplicationContext(), "Пароль должен быть не менее 10 цифр!", Toast.LENGTH_LONG).show();
    }
}
