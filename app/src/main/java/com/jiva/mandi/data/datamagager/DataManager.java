package com.jiva.mandi.data.datamagager;

import com.jiva.mandi.data.db.DbHelper;
import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.data.preference.PreferencesHelper;

import java.util.List;

import io.reactivex.Observable;



public interface DataManager extends DbHelper, PreferencesHelper {
    Observable<Boolean> getVillagesFromJson();
}
