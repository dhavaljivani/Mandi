package com.jiva.mandi.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jiva.mandi.data.model.db.Village;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface VillageDao {

    @Query("SELECT * FROM villages WHERE name LIKE :name LIMIT 1")
    Single<Village> findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Village> villages);

    @Query("SELECT * FROM villages")
    Single<List<Village>> loadAll();
}
