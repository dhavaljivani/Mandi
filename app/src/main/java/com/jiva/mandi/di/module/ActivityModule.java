package com.jiva.mandi.di.module;


import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.ui.base.BaseActivity;
import com.jiva.mandi.ui.base.ViewModelProviderFactory;
import com.jiva.mandi.ui.main.MainViewModel;
import com.jiva.mandi.ui.splash.SplashViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final BaseActivity<?, ?> activity;

    public ActivityModule(BaseActivity<?, ?> activity) {
        this.activity = activity;
    }


    @Provides
    MainViewModel provideMainViewModel(DataManager dataManager) {
        Supplier<MainViewModel> supplier = () -> new MainViewModel(dataManager);
        ViewModelProviderFactory<MainViewModel> factory = new ViewModelProviderFactory<>(MainViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    @Provides
    SplashViewModel provideSplashViewModel(DataManager dataManager) {
        Supplier<SplashViewModel> supplier = () -> new SplashViewModel(dataManager);
        ViewModelProviderFactory<SplashViewModel> factory = new ViewModelProviderFactory<>(SplashViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(SplashViewModel.class);
    }
}
