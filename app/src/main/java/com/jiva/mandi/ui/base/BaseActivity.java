package com.jiva.mandi.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

import javax.inject.Inject;


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity {

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection(getBuildComponent());
        super.onCreate(savedInstanceState);
        performDataBinding();
    }
    @SuppressWarnings("unused")
    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * Hide action bar.
     */
    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    /**
     * Get activity component.
     * @return ActivityComponent
     */
    private ActivityComponent getBuildComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(((MandiApp) getApplication()).appComponent)
                .activityModule(new ActivityModule(this))
                .build();
    }

    public abstract void performDependencyInjection(ActivityComponent buildComponent);

    /**
     * Perform data binding and execute pending binding.
     */
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

