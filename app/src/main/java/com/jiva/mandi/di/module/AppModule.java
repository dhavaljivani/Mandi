package com.jiva.mandi.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiva.mandi.data.datamagager.AppDataManager;
import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.data.db.AppDatabase;
import com.jiva.mandi.data.db.AppDbHelper;
import com.jiva.mandi.data.db.DbHelper;
import com.jiva.mandi.data.preference.AppPreferencesHelper;
import com.jiva.mandi.data.preference.PreferencesHelper;
import com.jiva.mandi.di.DatabaseInfo;
import com.jiva.mandi.di.PreferenceInfo;
import com.jiva.mandi.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @SuppressWarnings("SameReturnValue")
    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }


    @SuppressWarnings("SameReturnValue")
    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

}
