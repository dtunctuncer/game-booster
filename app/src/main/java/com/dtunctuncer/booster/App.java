package com.dtunctuncer.booster;

import android.app.Application;

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

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class App extends Application {
    private static ApplicationComponent applicationComponent;

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


        //Google Anaylicts
        AnalyticsTracker.initialize(this);

        AnalyticsUtils.trackEvent(EventCategories.ROOT_EVENT, "Root Check", "isRootAvailable : " + RootTools.isRootAvailable());

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
