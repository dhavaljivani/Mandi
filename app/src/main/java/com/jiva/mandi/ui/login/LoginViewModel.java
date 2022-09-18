package com.jiva.mandi.ui.login;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.data.model.LoginRequest;
import com.jiva.mandi.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private LoginRequest loginRequest;

    public LoginViewModel(DataManager dataManager) {
        super(dataManager);
        loginRequest = new LoginRequest("", "");
    }



    public void login() {
        setIsLoading(true);
        Disposable disposable = getDataManager().findUser(loginRequest.getUsername(), loginRequest.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    if (user.getUserId() != 0) {
                        getNavigator().onLoginSuccess();
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                });

        getCompositeDisposable().add(disposable);

    }


    public void checkAndInsertVillages() {
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().isVillageEmpty()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (response == null || response == 0) {
                                insertVillages();
                            }
                        }, throwable -> {
                            setIsLoading(false);
                            getNavigator().handleError(throwable);
                        }));
    }

    private void insertVillages() {
        getCompositeDisposable().add(
                getDataManager().getVillagesFromJson()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            setIsLoading(false);
                        }, throwable -> {
                            getNavigator().handleError(throwable);
                        }));
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }
}
