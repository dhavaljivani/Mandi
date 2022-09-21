package com.jiva.mandi.di.component;


import com.jiva.mandi.di.module.FragmentModule;
import com.jiva.mandi.di.scope.FragmentScope;
import com.jiva.mandi.ui.login.LoginFragment;
import com.jiva.mandi.ui.productsell.ProductSellFragment;
import com.jiva.mandi.ui.productsold.ProductSoldFragment;
import com.jiva.mandi.ui.register.RegisterFragment;

import dagger.Component;

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(LoginFragment fragment);
    void inject(RegisterFragment fragment);
    void inject (ProductSellFragment fragment);
    void inject (ProductSoldFragment fragment);
}
