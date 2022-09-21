package com.jiva.mandi.ui.register;

import androidx.room.EmptyResultSetException;

import com.google.gson.Gson;
import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.data.model.LoginResponse;
import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.ui.base.BaseViewModel;
import com.jiva.mandi.utils.AppUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {


    private User user;
    private final Gson mGson;

    public RegisterViewModel(DataManager dataManager, Gson gson) {
        super(dataManager);
        mGson = gson;
        user = new User();
        getAllVillages();
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
                }, throwable -> getNavigator().handleError(throwable));
        getCompositeDisposable().add(disposable);
    }

    public void checkAndCreateUser() {
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
                .subscribe(userId -> {
                    int id = Math.toIntExact(userId);
                    getUserDetailById(id);
                }, throwable -> getNavigator().handleError(throwable));

        getCompositeDisposable().add(disposable);
    }

    private void getUserDetailById(int userId) {
        Disposable disposable = getDataManager().findUserById(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    String json = mGson.toJson(loginResponse, LoginResponse.class);
                    getDataManager().setLoggedInUser(json);
                    getNavigator().SignUpSuccess();
                }, throwable -> getNavigator().handleError(throwable));

        getCompositeDisposable().add(disposable);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
