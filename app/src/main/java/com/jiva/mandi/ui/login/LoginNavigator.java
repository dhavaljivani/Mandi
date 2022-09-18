package com.jiva.mandi.ui.login;

import com.jiva.mandi.data.model.LoginResponse;

@SuppressWarnings("ALL")
public interface LoginNavigator {

    void handleError(Throwable throwable);

    void onLoginSuccess();
}
