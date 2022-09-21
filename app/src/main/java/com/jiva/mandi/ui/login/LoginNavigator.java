package com.jiva.mandi.ui.login;

@SuppressWarnings("ALL")
public interface LoginNavigator {

    void handleError(Throwable throwable);

    void onLoginSuccess();
}
