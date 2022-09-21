package com.jiva.mandi.di.component;

import com.jiva.mandi.di.module.ActivityModule;
import com.jiva.mandi.di.scope.ActivityScope;
import com.jiva.mandi.ui.main.MainActivity;
import com.jiva.mandi.ui.splash.SplashActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject (MainActivity mainActivity);
    void inject (SplashActivity splashActivity);
}
