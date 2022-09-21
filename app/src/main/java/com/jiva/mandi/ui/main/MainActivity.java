package com.jiva.mandi.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jiva.mandi.R;
import com.jiva.mandi.databinding.ActivityMainBinding;
import com.jiva.mandi.di.component.ActivityComponent;
import com.jiva.mandi.ui.base.BaseActivity;
import com.jiva.mandi.utils.AppConstants;

import java.util.HashSet;
import java.util.Set;

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
        setUpStartDestination();
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.productSellFragment);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(topLevelDestinations).build();
        NavigationUI.setupActionBarWithNavController(this, mNavController, appBarConfiguration);
        mNavController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setShowHideAnimationEnabled(false);
            }
            if (navDestination.getId() == R.id.loginFragment || navDestination.getId() == R.id.registerFragment) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                }
            } else {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(() -> {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }
                }, 350);

            }
        });
    }

    /**
     * Setup the start destination based on user logged in or not.
     */
    private void setUpStartDestination() {
        // get data from bundle
        boolean isUserLoggedIn = getIntent()
                .getBooleanExtra(AppConstants.IntentKey.IS_USER_LOGGED_IN, false);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();
            NavGraph navGraph = mNavController.getNavInflater().inflate(R.navigation.nav_graph);
            if (isUserLoggedIn) {
                navGraph.setStartDestination(R.id.productSellFragment);
            } else {
                navGraph.setStartDestination(R.id.loginFragment);
            }
            mNavController.setGraph(navGraph);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return mNavController.navigateUp() || super.onSupportNavigateUp();
    }
}