package com.dtunctuncer.booster.di.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.dtunctuncer.booster.App;
import com.dtunctuncer.booster.di.module.ApplicationModule;
import com.dtunctuncer.booster.utils.RxBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    SharedPreferences pref();

    App application();

    RxBus rxBus();

    Context context();

    SharedPreferences.Editor edit();
}
