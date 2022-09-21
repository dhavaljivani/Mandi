package com.jiva.mandi.data.datamagager;

import com.jiva.mandi.data.db.DbHelper;
import com.jiva.mandi.data.model.LoginResponse;
import com.jiva.mandi.data.preference.PreferencesHelper;

import io.reactivex.Observable;



public interface DataManager extends DbHelper, PreferencesHelper {
    Observable<Boolean> getVillagesFromJson();
    Observable<LoginResponse> getLoggedInUserData();
}
