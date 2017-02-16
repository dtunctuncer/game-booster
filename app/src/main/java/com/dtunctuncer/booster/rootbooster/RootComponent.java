package com.dtunctuncer.booster.rootbooster;

import com.dtunctuncer.booster.di.component.ApplicationComponent;
import com.dtunctuncer.booster.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = {RootModule.class}, dependencies = {ApplicationComponent.class})
public interface RootComponent {
    void inject(RootFragment fragment);
}
