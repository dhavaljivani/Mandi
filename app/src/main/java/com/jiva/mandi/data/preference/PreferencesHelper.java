package com.jiva.mandi.data.preference;

import com.jiva.mandi.data.model.LoginResponse;

@SuppressWarnings("ALL")
public interface PreferencesHelper {

    String getLoggedInUser();

    void setLoggedInUser(String loginResponse);
}
