package com.jiva.mandi.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * User database table entity class.
 * All seller details will be stored on this table.
 */
@Entity(tableName = "users",
        foreignKeys = {@ForeignKey(entity = Village.class, parentColumns = "id", childColumns = "village_id")},
        indices = {@Index(value = {"name"},
                unique = true)}
)
public class User {

    public User() {
    }

    @Ignore
    public User(String name, String loyaltyCardId, String mobileNumber, int villageId, String password) {
        this.name = name;
        this.loyaltyCardId = loyaltyCardId;
        this.mobileNumber = mobileNumber;
        this.villageId = villageId;
        this.password = password;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    @ColumnInfo(name = "loyalty_card_id")
    public String loyaltyCardId;

    @ColumnInfo(name = "mobile_number")
    public String mobileNumber;

    @ColumnInfo(name = "village_id", index = true)
    public int villageId;

    public String password;

}
