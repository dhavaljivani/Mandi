package com.jiva.mandi.data.db;

import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.data.model.db.Village;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }


    @Override
    public Observable<Boolean> insertUser(final User user) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.userDao().insert(user);
                return true;
            }
        });
    }

    @Override
    public Observable<User> getUserByName(String name) {
        return mAppDatabase.userDao().findByName(name).toObservable();
    }

    @Override
    public Observable<Boolean> insertVillages(List<Village> villages) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.villageDao().insertAll(villages);
                return true;
            }
        });

    }

    @Override
    public Observable<List<Village>> getAllVillages() {
        return mAppDatabase.villageDao().loadAll().toObservable();
    }
}
