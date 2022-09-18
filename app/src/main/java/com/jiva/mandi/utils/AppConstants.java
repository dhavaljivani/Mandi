package com.jiva.mandi.utils;
public final class AppConstants {


    public static final String DB_NAME = "mandi_app.db";
    public static final String PREF_NAME = "pref";
    public static final String VILLAGE_LIST = "village_list.json";
    public static final double REGISTERED_USER_LOYALTY_INDEX = 1.12;
    public static final double UNREGISTERED_USER_LOYALTY_INDEX = 0.98;


    private AppConstants() {
        // This utility class is not publicly instantiable
    }

    // Define all the intent key value pair
    public static final class IntentKey {
        public static final String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
    }
}
