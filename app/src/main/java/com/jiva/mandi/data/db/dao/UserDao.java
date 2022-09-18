package com.jiva.mandi.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jiva.mandi.data.model.LoginResponse;
import com.jiva.mandi.data.model.db.User;

import io.reactivex.Single;

@Dao
public interface UserDao {


    @Query("SELECT users.id as userId,users.name,users.loyalty_card_id as loyaltyCardId,villages.name as villageName," +
            "villages.selling_price as sellingPrice FROM users LEFT JOIN villages ON villages.id = users.village_id " +
            "WHERE mobile_number LIKE :number and password LIKE :password LIMIT 1")
    Single<LoginResponse> findUser(String number, String password);

    @Query("SELECT EXISTS(SELECT * FROM users WHERE mobile_number = :number)")
    Single<Boolean> isUserExist(String number);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("SELECT loyalty_card_id FROM users ORDER BY id DESC LIMIT 1")
    Single<String> getLastInsertedUser();

}
