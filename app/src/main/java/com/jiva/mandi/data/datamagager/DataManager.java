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

package com.jiva.mandi.data.datamagager;

import com.jiva.mandi.data.db.DbHelper;
import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.data.preference.PreferencesHelper;

import java.util.List;

import io.reactivex.Observable;



public interface DataManager extends DbHelper, PreferencesHelper {

    Observable<Boolean> insertUser(User user);

    Observable<Boolean> insertVillages(List<Village> villages);

    Observable<List<Village>> getAllVillages();

    Observable<Integer> isVillageEmpty();

}
