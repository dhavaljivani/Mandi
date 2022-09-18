package com.jiva.mandi.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jiva.mandi.R;
import com.jiva.mandi.databinding.ActivityMainBinding;
import com.jiva.mandi.di.component.ActivityComponent;
import com.jiva.mandi.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private NavController mNavController;

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder().build();

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if(navHostFragment != null){
            mNavController = navHostFragment.getNavController();
        }
        NavigationUI.setupActionBarWithNavController(this, mNavController, appBarConfiguration);

        mNavController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if(getSupportActionBar() != null){
                getSupportActionBar().setShowHideAnimationEnabled(false);
            }
            if (navDestination.getId() == R.id.loginFragment || navDestination.getId() == R.id.registerFragment) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                }
            }else{
                if (getSupportActionBar() != null) {
                    getSupportActionBar().show();
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        //
        if (!(mNavController.navigateUp() || super.onSupportNavigateUp())) {
            onBackPressed();
        }
        return true;
    }
}