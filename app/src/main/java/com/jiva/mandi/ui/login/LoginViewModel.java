package com.jiva.mandi.ui.login;

import com.google.gson.Gson;
import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.data.model.LoginRequest;
import com.jiva.mandi.data.model.LoginResponse;
import com.jiva.mandi.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private LoginRequest loginRequest;
    private final Gson mGson;

    public LoginViewModel(DataManager dataManager, Gson gson) {
        super(dataManager);
        mGson = gson;
        loginRequest = new LoginRequest();
    }


    public void login() {
        setIsLoading(true);
        Disposable disposable = getDataManager().findUser(loginRequest.getUsername(), loginRequest.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    if (user.getUserId() != 0) {
                        String json = mGson.toJson(user, LoginResponse.class);
                        getDataManager().setLoggedInUser(json);
                        getNavigator().onLoginSuccess();
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                });

        getCompositeDisposable().add(disposable);

    }




    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }
}
