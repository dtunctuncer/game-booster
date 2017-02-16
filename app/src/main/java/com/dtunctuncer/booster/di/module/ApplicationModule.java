package com.dtunctuncer.booster.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dtunctuncer.booster.App;
import com.dtunctuncer.booster.utils.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    App provideApplication() {
        return this.app;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(App application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    SharedPreferences.Editor provideEditor(SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

    @Provides
    @Singleton
    RxBus rxBus() {
        return new RxBus();
    }

    @Provides
    @Singleton
    Context provideAplicationContext() {
        return app.getApplicationContext();
    }
}