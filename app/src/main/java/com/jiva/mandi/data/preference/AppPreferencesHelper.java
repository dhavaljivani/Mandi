package com.jiva.mandi.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiva.mandi.di.PreferenceInfo;

import javax.inject.Inject;

@SuppressWarnings("ALL")
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_USER_DETAIL = "PREF_KEY_USER_DETAIL";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getLoggedInUser() {
        return mPrefs.getString(PREF_KEY_USER_DETAIL, "");
    }

    @Override
    public void setLoggedInUser(String loginResponseJson) {
        mPrefs.edit().putString(PREF_KEY_USER_DETAIL, loginResponseJson).apply();
    }

    @Override
    public void clearPreferences() {
        mPrefs.edit().clear().apply();
    }
}
