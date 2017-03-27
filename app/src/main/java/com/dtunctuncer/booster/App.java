package com.dtunctuncer.booster;

import android.app.Application;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.dtunctuncer.booster.core.EventCategories;
import com.dtunctuncer.booster.di.component.ApplicationComponent;
import com.dtunctuncer.booster.di.component.DaggerApplicationComponent;
import com.dtunctuncer.booster.di.module.ApplicationModule;
import com.dtunctuncer.booster.utils.analytics.AnalyticsTracker;
import com.dtunctuncer.booster.utils.analytics.AnalyticsUtils;
import com.dtunctuncer.booster.utils.timber.CrashReportTree;
import com.stericson.RootTools.RootTools;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class App extends Application {

    private static ApplicationComponent applicationComponent;
    @Inject
    SharedPreferences preferences;
    @Inject
    SharedPreferences.Editor editor;

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        //di
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);


        //Google Anaylicts
        AnalyticsTracker.initialize(this);

        if (!preferences.getBoolean(EventCategories.ROOT_EVENT, false)) {
            AnalyticsUtils.trackEvent(EventCategories.ROOT_EVENT, "Root Check", "isRootAvailable : " + RootTools.isRootAvailable());
            editor.putBoolean(EventCategories.ROOT_EVENT, true);
            editor.apply();
        }

        //Crashlatics
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();

        Fabric.with(this, new Crashlytics.Builder().core(core).build());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportTree());
        }
    }
}