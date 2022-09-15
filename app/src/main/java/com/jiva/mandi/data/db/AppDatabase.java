package com.jiva.mandi.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jiva.mandi.data.db.dao.UserDao;
import com.jiva.mandi.data.db.dao.VillageDao;
import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.data.model.db.Village;


@Database(entities = {User.class, Village.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract VillageDao villageDao();

}
