package com.jiva.mandi.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.utils.DataConverter;

import java.util.List;

import io.reactivex.Single;


@SuppressWarnings("ALL")
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



