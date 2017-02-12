package com.dtunctuncer.booster;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.dtunctuncer.booster.core.EventCategories;
import com.dtunctuncer.booster.utils.AnalyticsTracker;
import com.dtunctuncer.booster.utils.AnalyticsUtils;
import com.dtunctuncer.booster.utils.CrashReportTree;
import com.stericson.RootTools.RootTools;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


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
