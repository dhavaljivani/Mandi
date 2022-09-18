package com.jiva.mandi.data.datamagager;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiva.mandi.data.db.DbHelper;
import com.jiva.mandi.data.model.LoginResponse;
import com.jiva.mandi.data.model.UserResponse;
import com.jiva.mandi.data.model.VillagesList;
import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.data.preference.PreferencesHelper;
import com.jiva.mandi.utils.AppConstants;
import com.jiva.mandi.utils.AppUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@Singleton
public class AppDataManager implements DataManager {
    private final DbHelper mDbHelper;
    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final Gson mGson;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mGson = gson;
    }

    @Override
    public String getLoggedInUser() {
        return mPreferencesHelper.getLoggedInUser();
    }

    @Override
    public void setLoggedInUser(String loginResponse) {
        mPreferencesHelper.setLoggedInUser(loginResponse);
    }


    @Override
    public Observable<LoginResponse> findUser(String mobileNumber, String password) {
        return mDbHelper.findUser(mobileNumber, password);
    }

    @Override
    public Observable<List<UserResponse>> getAllUser() {
        return mDbHelper.getAllUser();
    }

    @Override
    public Observable<LoginResponse> findUserById(int userId) {
        return mDbHelper.findUserById(userId);
    }

    @Override
    public Observable<Long> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<Boolean> isUserExist(String phoneNumber) {
        return mDbHelper.isUserExist(phoneNumber);
    }

    @Override
    public Observable<Boolean> insertVillages(List<Village> villageList) {
        return mDbHelper.insertVillages(villageList);
    }

    @Override
    public Observable<List<Village>> getAllVillages() {
        return mDbHelper.getAllVillages();
    }

    @Override
    public Observable<Integer> isVillageEmpty() {
        return mDbHelper.isVillageEmpty();
    }

    @Override
    public Observable<String> getLastInsertedUser() {
        return mDbHelper.getLastInsertedUser();
    }

    @Override
    public Observable<Boolean> getVillagesFromJson() {
        VillagesList villagesList = mGson.fromJson(AppUtils.loadJSONFromAsset(mContext, AppConstants.VILLAGE_LIST), VillagesList.class);
        return insertVillages(villagesList.getVillages());
    }

    @Override
    public Observable<LoginResponse> getLoggedInUserData() {
        String jsonString = getLoggedInUser();
        if (!TextUtils.isEmpty(jsonString)) {
            LoginResponse loginResponse = mGson.fromJson(jsonString, LoginResponse.class);
            return Observable.just(loginResponse);
        }
        return Observable.just(new LoginResponse());
    }
}
