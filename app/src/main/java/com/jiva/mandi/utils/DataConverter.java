package com.jiva.mandi.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiva.mandi.data.model.db.Village;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {

    @SuppressWarnings("unused")
    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<Village> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Village>>() {
        }.getType();
        return gson.toJson(optionValues, type);
    }

    @SuppressWarnings("unused")
    @TypeConverter // note this annotation
    public List<Village> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Village>>() {
        }.getType();
        return gson.fromJson(optionValuesString, type);
    }

}