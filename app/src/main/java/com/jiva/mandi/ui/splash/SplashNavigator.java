package com.jiva.mandi.ui.splash;

import com.jiva.mandi.ui.base.BaseNavigator;

@SuppressWarnings("ALL")
public interface SplashNavigator extends BaseNavigator {

    /**
     * @param isLoggedIn is user is logged in or not
     */
    void openMainActivity(boolean isLoggedIn);
}
