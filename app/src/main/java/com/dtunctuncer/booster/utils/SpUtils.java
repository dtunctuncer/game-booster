package com.dtunctuncer.booster.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dtunctuncer.booster.core.BoosterModes;

public class SpUtils {
    public static final String CURRENT_MODE = "CURRENT_MODE";

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    public SpUtils(Context context) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = this.prefs.edit();
    }

    public SpUtils(SharedPreferences.Editor prefsEditor, SharedPreferences prefs) {
        this.prefs = prefs;
        this.prefsEditor = prefsEditor;
    }

    public void setString(String key, String value) {
        this.prefsEditor.putString(key, value);
        this.prefsEditor.apply();
    }

    public String getString(String key, String def) {
        return this.prefs.getString(key, def);
    }

    public void setBoolean(String key, boolean value) {
        this.prefsEditor.putBoolean(key, value);
        this.prefsEditor.apply();
    }

    public boolean getBoolean(String key, boolean def) {
        return this.prefs.getBoolean(key, def);
    }


    public int getCurrentMode() {
        return this.prefs.getInt(CURRENT_MODE, 0);
    }

    public void setCurrentMode(int mode) {
        switch (mode) {
            case BoosterModes.NO_MODE /*0*/:
                this.prefsEditor.putInt(CURRENT_MODE, 0);
                break;
            case BoosterModes.HIGH_MODE/*1*/:
                this.prefsEditor.putInt(CURRENT_MODE, 1);
                break;
            case BoosterModes.ULTRA_MODE/*2*/:
                this.prefsEditor.putInt(CURRENT_MODE, 2);
                break;
        }
        this.prefsEditor.apply();
    }
}
