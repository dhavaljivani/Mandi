package com.jiva.mandi.ui.login;

import android.os.Bundle;
import android.view.View;

import com.jiva.mandi.BR;
import com.jiva.mandi.R;
import com.jiva.mandi.databinding.ActivityLoginBinding;
import com.jiva.mandi.di.component.ActivityComponent;
import com.jiva.mandi.ui.base.BaseActivity;
import com.jiva.mandi.ui.productsell.ProductSellActivity;
import com.jiva.mandi.ui.register.RegisterActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator, View.OnClickListener {

    @Override
    public int getBindingVariable() {
        return BR.loginViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding mActivityLoginBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        mViewModel.checkAndInsertVillages();

        //Set on click listener on clickable view.
        mActivityLoginBinding.btnLogin.setOnClickListener(this);
        mActivityLoginBinding.tvRegister.setOnClickListener(this);
        mActivityLoginBinding.tvSkip.setOnClickListener(this);

    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }


    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void login() {

    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            moveActivity(this, ProductSellActivity.class,true);
        } else if (v.getId() == R.id.tvRegister) {
            moveActivity(this, RegisterActivity.class, false);
        } else if (v.getId() == R.id.tvSkip) {
            moveActivity(this, ProductSellActivity.class,true);
        }
    }
}