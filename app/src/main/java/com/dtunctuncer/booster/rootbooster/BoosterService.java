package com.dtunctuncer.booster.rootbooster;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.dtunctuncer.booster.R;
import com.dtunctuncer.booster.utils.SpUtils;

public class BoosterService extends Service {
    BoosterModeManager boosterModeManager;
    SpUtils dataManager;
    boolean runOrNot = true;

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public void onCreate() {
        super.onCreate();
        this.dataManager = new SpUtils(getApplicationContext());
        this.boosterModeManager = new BoosterModeManager(getApplicationContext());
        performOnBackgroundThread().start();
    }

    public Thread performOnBackgroundThread() {
        return new Thread() {
            public void run() {
                while (BoosterService.this.runOrNot) {
                    try {
                        Thread.sleep(150);
                        switch (BoosterService.this.dataManager.getCurrentMode()) {
                            case R.styleable.View_android_theme /*0*/:
                                BoosterService.this.runOrNot = false;
                                BoosterService.this.stopSelf();
                                break;
                            case R.styleable.View_android_focusable /*1*/:
                                BoosterService.this.boosterModeManager.setMode(1);
                                break;
                            case R.styleable.View_paddingStart /*2*/:
                                BoosterService.this.boosterModeManager.setMode(2);
                                break;
                            default:
                                break;
                        }
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        };
    }
}