package com.jiva.mandi.ui.register;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.jiva.mandi.BR;
import com.jiva.mandi.R;
import com.jiva.mandi.databinding.ActivityLoginBinding;
import com.jiva.mandi.databinding.ActivityRegisterBinding;
import com.jiva.mandi.di.component.ActivityComponent;
import com.jiva.mandi.ui.base.BaseActivity;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> implements View.OnClickListener {

    @Override
    public int getBindingVariable() {return BR.registerViewModel;}

    @Override
    public int getLayoutId() {return R.layout.activity_register;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding mActivityRegisterBinding = getViewDataBinding();

        //Set on click listener on clickable view.
        mActivityRegisterBinding.btnSignUp.setOnClickListener(this);
        mActivityRegisterBinding.ivBack.setOnClickListener(this);

        setUpVillageSpinner();
    }

    private void setUpVillageSpinner() {
        VillageSpinnerAdapter villageSpinnerAdapter = new VillageSpinnerAdapter(this,
                getResources().getStringArray(R.array.villageArray), R.layout.villag_dropdown_text,
                R.layout.village_name_dropdown);
        AppCompatSpinner villageSpinner = findViewById(R.id.spVillage);
        villageSpinner.setAdapter(villageSpinnerAdapter);
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack) {
            finish();
        } else if (v.getId() == R.id.btnSignUp) {
            moveActivity(this, RegisterActivity.class, false);
        }
    }
}