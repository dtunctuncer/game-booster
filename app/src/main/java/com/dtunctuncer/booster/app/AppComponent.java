package com.dtunctuncer.booster.app;

import com.dtunctuncer.booster.di.component.ApplicationComponent;
import com.dtunctuncer.booster.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = {AppModule.class}, dependencies = {ApplicationComponent.class})
public interface AppComponent {
    void inject(AppFragment appFragment);
}
