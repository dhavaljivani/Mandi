package com.jiva.mandi.data.db;

import com.jiva.mandi.data.model.LoginResponse;
import com.jiva.mandi.data.model.UserResponse;
import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.data.model.db.Village;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


@SuppressWarnings("ALL")
@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }


    @Override
    public Observable<LoginResponse> findUser(String mobileNumber, String password) {
        return mAppDatabase.userDao().findUser(mobileNumber, password).toObservable();
    }

    @Override
    public Observable<LoginResponse> findUserById(int userId) {
        return mAppDatabase.userDao().findUserById(userId).toObservable();
    }

    @Override
    public Observable<List<UserResponse>> getAllUser() {
        return mAppDatabase.userDao().getAllUser().toObservable();
    }

    @Override
    public Observable<Long> insertUser(final User user) {
        return mAppDatabase.userDao().insert(user).toObservable();
    }

    @Override
    public Observable<Boolean> isUserExist(String phoneNumber) {
        return mAppDatabase.userDao().isUserExist(phoneNumber).toObservable();
    }

    @Override
    public Observable<Boolean> insertVillages(List<Village> villages) {
        return Observable.fromCallable(() -> {
            mAppDatabase.villageDao().insertAll(villages);
            return true;
        });

    }

    @Override
    public Observable<List<Village>> getAllVillages() {
        return mAppDatabase.villageDao().loadAll().toObservable();
    }

    @Override
    public Observable<Integer> isVillageEmpty() {
        return mAppDatabase.villageDao().isVillageIsEmpty().toObservable();
    }

    @Override
    public Observable<String> getLastInsertedUser() {
        return mAppDatabase.userDao().getLastInsertedUser().toObservable();
    }
}
