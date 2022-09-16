package com.jiva.mandi.ui.register;

public interface RegisterNavigator {

    void handleError(Throwable throwable);

    void login();

    void openMainActivity();
}
