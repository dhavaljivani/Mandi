/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.jiva.mandi.ui.login;

import android.text.TextUtils;

import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.ui.base.BaseViewModel;
import com.jiva.mandi.utils.AppUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {


    public LoginViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public boolean isEmailAndPasswordValid(String email, String password) {
        // validate email and password
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        if (!AppUtils.isEmailValid(email)) {
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    public void insertVillages() {

        ArrayList<Village> villages = new ArrayList<>();
        villages.add(new Village("Ramgarh", 120.08));
        villages.add(new Village("Champaner", 125.08));
        villages.add(new Village("Pune", 130.00));
        villages.add(new Village("Ahmedabad", 131.02));
        villages.add(new Village("Rajkot", 135.09));

        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().insertVillages(villages)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            setIsLoading(false);
                            getNavigator().openMainActivity();
                        }, throwable -> {
                            setIsLoading(false);
                            getNavigator().handleError(throwable);
                        }));
    }

    public void login() {

        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().insertUser(new User("Dhaval Jivani", "S100",
                        "9638083903", 1, "Test@123"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().openMainActivity();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
}
