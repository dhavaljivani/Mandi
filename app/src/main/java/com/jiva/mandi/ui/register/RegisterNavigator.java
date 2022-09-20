package com.jiva.mandi.ui.register;

import com.jiva.mandi.ui.base.BaseNavigator;

@SuppressWarnings("ALL")
public interface RegisterNavigator extends BaseNavigator {
    /**
     * This will call after signup sucess.
     */
    void SignUpSuccess();

    /**
     * This will call if user is already exist.
     */
    void UserAlreadyExist();
}
