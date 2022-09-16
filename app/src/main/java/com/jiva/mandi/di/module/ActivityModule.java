package com.jiva.mandi.di.module;


import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.ui.base.BaseActivity;
import com.jiva.mandi.ui.base.ViewModelProviderFactory;
import com.jiva.mandi.ui.login.LoginViewModel;
import com.jiva.mandi.ui.register.RegisterViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private BaseActivity<?, ?> activity;

    public ActivityModule(BaseActivity<?, ?> activity) {
        this.activity = activity;
    }


    @Provides
    LoginViewModel provideLoginViewModel(DataManager dataManager) {
        Supplier<LoginViewModel> supplier = () -> new LoginViewModel(dataManager);
        ViewModelProviderFactory<LoginViewModel> factory = new ViewModelProviderFactory<>(LoginViewModel.class, supplier);
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(LoginViewModel.class);
    }

    @Provides
    RegisterViewModel provideRegisterViewModel(DataManager dataManager) {
        Supplier<RegisterViewModel> supplier = () -> new RegisterViewModel(dataManager);
        ViewModelProviderFactory<RegisterViewModel> factory = new ViewModelProviderFactory<>(RegisterViewModel.class, supplier);
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(RegisterViewModel.class);
    }


}
