package com.jiva.mandi.ui.splash;

import android.util.Log;

import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    public SplashViewModel(DataManager dataManager) {
        super(dataManager);
        checkAndInsertVillages();
        checkUserLoggedInOrNot();
    }

    public void checkUserLoggedInOrNot() {
        Disposable disposable = getDataManager().getLoggedInUserData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> getNavigator().openMainActivity(user != null
                                && user.getUserId() != 0),
                        throwable -> getNavigator().handleError(throwable));
        getCompositeDisposable().add(disposable);

    }

    public void checkAndInsertVillages() {
        Disposable disposable = getDataManager().isVillageEmpty()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response == null || response == 0) {
                        insertVillages();
                    }
                }, throwable -> getNavigator().handleError(throwable));
        getCompositeDisposable().add(disposable);

    }

    private void insertVillages() {
        Disposable disposable = getDataManager().getVillagesFromJson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> Log.d("Insert Village", "Village Successfully inserted"), throwable -> getNavigator().handleError(throwable));
        getCompositeDisposable().add(disposable);
    }
}
