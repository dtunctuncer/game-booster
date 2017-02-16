package com.dtunctuncer.booster.app;

import com.dtunctuncer.booster.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private IAppView view;

    public AppModule(IAppView view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    IAppView provideView() {
        return view;
    }
}