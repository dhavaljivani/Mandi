package com.jiva.mandi.data.preference;

@SuppressWarnings("ALL")
public interface PreferencesHelper {

    String getLoggedInUser();

    void setLoggedInUser(String loginResponse);

    void clearPreferences();
}
