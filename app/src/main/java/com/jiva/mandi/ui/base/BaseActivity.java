package com.jiva.mandi.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.jiva.mandi.MandiApp;
import com.jiva.mandi.R;
import com.jiva.mandi.di.component.ActivityComponent;
import com.jiva.mandi.di.component.DaggerActivityComponent;
import com.jiva.mandi.di.module.ActivityModule;
import com.jiva.mandi.utils.AppUtils;
import com.jiva.mandi.utils.NetworkUtils;

import javax.inject.Inject;


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements BaseFragment.Callback {

    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;

    private T mViewDataBinding;

    @Inject
    protected V mViewModel;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    @SuppressWarnings("SameReturnValue")
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    @SuppressWarnings("SameReturnValue")
    public abstract
    @LayoutRes
    int getLayoutId();


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection(getBuildComponent());
        super.onCreate(savedInstanceState);
        performDataBinding();
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void showActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }


    private ActivityComponent getBuildComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(((MandiApp) getApplication()).appComponent)
                .activityModule(new ActivityModule(this))
                .build();
    }

    public abstract void performDependencyInjection(ActivityComponent buildComponent);

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }


    /**
     * @param context          activity or fragment context
     * @param destinationClass Destination navigation class
     * @param finish           Boolean to finish the current activity
     * @param clearStack       Clear the activity stack
     * @param bundle           Bundle data
     */
    public void moveActivity(Context context, Class<?> destinationClass, boolean finish, boolean clearStack,
                             Bundle bundle) {
        Intent intent = new Intent(context, destinationClass);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        if (clearStack) {
            intent = intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);
        Activity activity = (Activity) context;
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if (finish) {
            ((Activity) context).finish();
        }
    }

}

