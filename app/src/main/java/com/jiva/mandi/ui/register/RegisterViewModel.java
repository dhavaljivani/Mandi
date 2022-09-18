package com.jiva.mandi.ui.register;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.room.EmptyResultSetException;

import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.ui.base.BaseViewModel;
import com.jiva.mandi.utils.AppUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.flowable.FlowableCache;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {

    private MutableLiveData<List<Village>> villageList;
    private User user;

    public RegisterViewModel(DataManager dataManager) {
        super(dataManager);
        user = new User();
    }

    public void getAllVillages() {
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().getAllVillages()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            villageList.setValue(response);
                            setIsLoading(false);

                        }, throwable -> {
                            setIsLoading(false);
                            getNavigator().handleError(throwable);
                        }));
    }

    public void isUserExist() {
        Disposable disposable = getDataManager().isUserExist(user.mobileNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isUserExist -> {
                    if (!isUserExist) {
                        createNewUser();
                    } else {
                        getNavigator().UserAlreadyExist();
                    }
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                });
        getCompositeDisposable().add(disposable);
    }

    public void checkAndCreateUser() {
        setIsLoading(true);
        Disposable disposable = getDataManager().getLastInsertedUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loyaltyId -> {
                    String newLoyaltyId = AppUtils.getNewLoyaltyId(loyaltyId);
                    user.setLoyaltyCardId(newLoyaltyId);
                    isUserExist();
                }, throwable -> {
                    if (throwable instanceof EmptyResultSetException) {
                        String newLoyaltyId = AppUtils.getNewLoyaltyId("");
                        user.setLoyaltyCardId(newLoyaltyId);
                        isUserExist();
                    } else {
                        getNavigator().handleError(throwable);
                    }
                });
        getCompositeDisposable().add(disposable);
    }

    private void createNewUser() {
        Disposable disposable = getDataManager().insertUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getNavigator().SignUpSuccess();
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                });

        getCompositeDisposable().add(disposable);
    }

    public MutableLiveData<List<Village>> getVillageList() {
        if (villageList == null) {
            villageList = new MutableLiveData<>();
        }
        return villageList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
