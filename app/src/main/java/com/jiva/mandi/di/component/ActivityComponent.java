package com.jiva.mandi.di.component;

import com.jiva.mandi.di.module.ActivityModule;
import com.jiva.mandi.di.scope.ActivityScope;
import com.jiva.mandi.ui.login.LoginActivity;
import com.jiva.mandi.ui.productsell.ProductSellActivity;
import com.jiva.mandi.ui.productsold.ProductSoldActivity;
import com.jiva.mandi.ui.register.RegisterActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject (LoginActivity loginActivity);
    void inject (RegisterActivity registerActivity);
    void inject (ProductSellActivity productSellActivity);
    void inject (ProductSoldActivity productSellActivity);
}
