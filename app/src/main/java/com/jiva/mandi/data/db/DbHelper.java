package com.jiva.mandi.data.db;

import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.data.model.db.Village;

import java.util.List;

import io.reactivex.Observable;


public interface DbHelper {

    Observable<Boolean> insertUser(final User user);

    Observable<User> getUserByName(final String name);

    Observable<Boolean> insertVillages(final List<Village> villages);

    Observable<List<Village>> getAllVillages();

    Observable<Integer> isVillageEmpty();

}
