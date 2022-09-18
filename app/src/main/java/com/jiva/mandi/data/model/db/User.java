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
        indices = {@Index(value = {"name","mobile_number"},
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

    @Ignore
    public String plainPassword;

    @Ignore
    public String getName() {
        return name;
    }

    @Ignore
    public void setName(String name) {
        this.name = name;
    }

    @Ignore
    public String getLoyaltyCardId() {
        return loyaltyCardId;
    }

    @Ignore
    public void setLoyaltyCardId(String loyaltyCardId) {
        this.loyaltyCardId = loyaltyCardId;
    }

    @Ignore
    public String getMobileNumber() {
        return mobileNumber;
    }

    @Ignore
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Ignore
    public int getVillageId() {
        return villageId;
    }

    @Ignore
    public void setVillageId(int villageId) {
        this.villageId = villageId;
    }

    @Ignore
    public String getPassword() {
        return password;
    }

    @Ignore
    public void setPassword(String password) {
        this.password = password;
    }
}
