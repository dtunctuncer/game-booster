package com.dtunctuncer.booster.rootbooster;

import android.content.Context;
import android.content.SharedPreferences;

import com.dtunctuncer.booster.di.scope.FragmentScope;
import com.dtunctuncer.booster.notification.ModeNotification;
import com.dtunctuncer.booster.utils.BoosterModeManager;
import com.dtunctuncer.booster.utils.SpUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class RootModule {
    private IRootView view;

    public RootModule(IRootView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    IRootView provideView() {
        return view;
    }

    @FragmentScope
    @Provides
    SpUtils provideSpUtils(SharedPreferences preferences, SharedPreferences.Editor editor) {
        return new SpUtils(editor, preferences);
    }

    @FragmentScope
    @Provides
    BoosterModeManager provideBoosterModeManager() {
        return new BoosterModeManager();
    }

    @FragmentScope
    @Provides
    ModeNotification provideModeNotification(Context context) {
        return new ModeNotification(context);
    }
}