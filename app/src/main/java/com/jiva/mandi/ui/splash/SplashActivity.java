package com.jiva.mandi.ui.splash;

import android.os.Bundle;

import com.jiva.mandi.BR;
import com.jiva.mandi.R;
import com.jiva.mandi.databinding.ActivitySplashBinding;
import com.jiva.mandi.di.component.ActivityComponent;
import com.jiva.mandi.ui.base.BaseActivity;
import com.jiva.mandi.ui.main.MainActivity;
import com.jiva.mandi.utils.AppConstants;
import com.jiva.mandi.utils.AppUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator {

    @Override
    public int getBindingVariable() {
        return BR.splashViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        mViewModel.setNavigator(this);
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @SuppressWarnings("ALL")
    @Override
    public void handleError(Throwable throwable) {
        AppUtils.handleException(throwable);
    }

    @SuppressWarnings("ALL")
    @Override
    public void openMainActivity(boolean isLoggedIn) {
        Disposable disposable = Completable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(AppConstants.IntentKey.IS_USER_LOGGED_IN, isLoggedIn);
                    moveActivity(this, MainActivity.class, true, true, bundle);
                });
        mViewModel.getCompositeDisposable().add(disposable);
    }
}