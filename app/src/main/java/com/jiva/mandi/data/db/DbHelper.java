package com.jiva.mandi.data.db;

import com.jiva.mandi.data.model.LoginResponse;
import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.data.model.db.Village;

import java.util.List;

import io.reactivex.Observable;

@SuppressWarnings("ALL")
public interface DbHelper {

    Observable<Boolean> insertUser(final User user);

    Observable<Boolean> isUserExist(final String phoneNumber);

    Observable<Boolean> insertVillages(final List<Village> villages);

    Observable<List<Village>> getAllVillages();

    Observable<Integer> isVillageEmpty();

    Observable<String> getLastInsertedUser();

    Observable<LoginResponse> findUser(String mobileNumber, String password);

}
