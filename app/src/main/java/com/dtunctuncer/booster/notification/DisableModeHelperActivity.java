package com.dtunctuncer.booster.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dtunctuncer.booster.rootbooster.BoosterModeManager;
import com.dtunctuncer.booster.utils.SpUtils;

public class DisableModeHelperActivity extends Activity {
    public static final String CLEAR_ACTION = "com.dtunctuncer.booster.CLEAR_ACTION";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ModeNotification(getApplicationContext()).startNotification(0);
        new BoosterModeManager(getApplicationContext()).setMode(0);
        new SpUtils(getApplicationContext()).setCurrentMode(0);
        sendBroadcast(new Intent(CLEAR_ACTION));
        finish();
    }
}