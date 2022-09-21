package com.jiva.mandi.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Village database table entity class.
 * All villages with their selling price per kilogram will be stored on this table.
 */
@SuppressWarnings("ALL")
@Entity(tableName = "villages", indices = {@Index(value = {"name"},
        unique = true)})
public class Village {

    public Village() {
    }

    @Ignore
    public Village(String name, double sellingPrice) {
        this.name = name;
        this.sellingPrice = sellingPrice;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    @ColumnInfo(name = "selling_price")
    public double sellingPrice;

    @Ignore
    public int getId() {
        return id;
    }
    @Ignore
    public void setId(int id) {
        this.id = id;
    }
    @Ignore
    public String getName() {
        return name;
    }
    @Ignore
    public void setName(String name) {
        this.name = name;
    }
    @Ignore
    public double getSellingPrice() {
        return sellingPrice;
    }
    @Ignore
    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
