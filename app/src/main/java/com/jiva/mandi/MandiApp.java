package com.jiva.mandi;

import android.app.Application;

import com.jiva.mandi.di.component.AppComponent;
import com.jiva.mandi.di.component.DaggerAppComponent;


public class MandiApp extends Application {

    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();

        appComponent.inject(this);


    }
}
