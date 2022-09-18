package com.jiva.mandi.ui.splash;

@SuppressWarnings("ALL")
public interface SplashNavigator {

    void handleError(Throwable throwable);

    void openMainActivity(boolean isLoggedIn);
}
