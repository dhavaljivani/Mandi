package com.jiva.mandi.di.component;

import android.app.Application;

import com.jiva.mandi.MandiApp;
import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;


@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MandiApp app);

    DataManager getDataManager();


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
