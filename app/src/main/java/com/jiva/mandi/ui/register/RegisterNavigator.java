package com.jiva.mandi.ui.register;

@SuppressWarnings("ALL")
public interface RegisterNavigator {
    void handleError(Throwable throwable);
    void SignUpSuccess();
    void UserAlreadyExist();
}
