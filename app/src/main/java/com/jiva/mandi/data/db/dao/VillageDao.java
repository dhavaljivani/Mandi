package com.jiva.mandi.data.db.dao;

import androidx.annotation.WorkerThread;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.utils.DataConverter;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Single;


@Dao
public interface VillageDao {

    @Query("SELECT * FROM villages WHERE name LIKE :name LIMIT 1")
    Single<Village> findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @TypeConverters(DataConverter.class)
    void insertAll(List<Village> villages);

    @Query("SELECT * FROM villages")
    Single<List<Village>> loadAll();

    @Query("SELECT COUNT() FROM villages")
    Single<Integer> isVillageIsEmpty();
}



