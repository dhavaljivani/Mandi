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
import androidx.appcompat.widget.Toolbar;
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
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
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

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
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


    public void showLoading() {
        hideLoading();
        mProgressDialog = AppUtils.showLoadingDialog(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }


    /**
     * @param context          context
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
        if (finish) {
            ((Activity) context).finish();
        }
    }

    /**
     * @param context          context
     * @param destinationClass Destination navigation class
     * @param finish           Boolean to finish the current activity
     */
    public void moveActivity(Context context, Class<?> destinationClass, boolean finish) {
        moveActivity(context, destinationClass, finish, false, null);
    }

    /**
     * @param context          context
     * @param destinationClass Destination navigation class
     * @param finish           Boolean to finish the current activity
     * @param clearStack       Clear the activity stack
     */
    public void moveActivity(Context context, Class<?> destinationClass, boolean finish,
                             boolean clearStack) {
        moveActivity(context, destinationClass, finish, clearStack, null);
    }


    /**
     * @param isShowBack Using this boolean param the custom navigation back button can
     *                   show/hide in toolbar.
     */
    public void setupToolbar(boolean isShowBack, String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setTitle(title);
            }

            if (isShowBack) {
                toolbar.setNavigationIcon(R.drawable.ic_arrow_white);
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            }
        }
    }
}

