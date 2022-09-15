package com.jiva.mandi.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jiva.mandi.data.model.db.User;

import io.reactivex.Single;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE name LIKE :name LIMIT 1")
    Single<User> findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);
}
