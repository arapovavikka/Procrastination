package com.dreamteam4140.stop.model;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private static AppPreferences sPrefs;

    private static final String APP_PREFERENCES = "user_pref";


    public enum Key {
        SETTINGS_PASSWORD_STR,
        PASSWORD_ENABLED,
        TIMER_TIME
    }

    private AppPreferences(Context context) {
        mPrefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

    }

    public static AppPreferences GetInstance(Context context)
    {
        if (sPrefs == null)
            sPrefs = new AppPreferences(context.getApplicationContext());
        return sPrefs;
    }

    public void put(Key key, String val) {
        doEdit();
        mEditor.putString(key.name(), val);
        doCommit();
    }

    public void put(Key key, int val) {
        doEdit();
        mEditor.putInt(key.name(), val);
        doCommit();
    }

    public void put(Key key, boolean val) {
        doEdit();
        mEditor.putBoolean(key.name(), val);
        doCommit();
    }

    public String getString(Key key, String defaultValue) {
        return mPrefs.getString(key.name(), defaultValue);
    }

    public String getString(Key key) {
        return mPrefs.getString(key.name(), null);
    }

    public int getInt(Key key) {
        return mPrefs.getInt(key.name(), 0);
    }

    public int getInt(Key key, int defaultValue) {
        return mPrefs.getInt(key.name(), defaultValue);
    }

    public boolean getBool(Key key) {return mPrefs.getBoolean(key.name(), false);}

    public boolean getBool(Key key, boolean defaultValue) {
        return mPrefs.getBoolean(key.name(), defaultValue);
    }

    private void doEdit() {
        if (mEditor == null) {
            mEditor = mPrefs.edit();
        }
    }

    private void doCommit() {
        if (mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }

}
