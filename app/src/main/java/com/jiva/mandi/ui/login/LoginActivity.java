package com.jiva.mandi.ui.login;

import android.os.Bundle;

import com.jiva.mandi.BR;
import com.jiva.mandi.R;
import com.jiva.mandi.databinding.ActivityLoginBinding;
import com.jiva.mandi.di.component.ActivityComponent;
import com.jiva.mandi.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginViewModel> implements LoginNavigator {

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
        com.jiva.mandi.databinding.ActivityLoginBinding mActivityLoginBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        mViewModel.checkAndInsertVillages();

        mActivityLoginBinding.login.setOnClickListener(v -> {
            mViewModel.login();
        });
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
}