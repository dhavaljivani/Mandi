package com.jiva.mandi.data.datamagager;

import android.content.Context;

import com.google.gson.Gson;
import com.jiva.mandi.data.db.DbHelper;
import com.jiva.mandi.data.model.LoginResponse;
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

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
    }

    @Override
    public Observable<LoginResponse> findUser(String mobileNumber, String password) {
        return mDbHelper.findUser(mobileNumber, password);
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
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
        Gson mGson = new Gson();
        VillagesList villagesList = mGson.fromJson(AppUtils.loadJSONFromAsset(mContext, AppConstants.VILLAGE_LIST), VillagesList.class);
        return insertVillages(villagesList.getVillages());
    }


}
