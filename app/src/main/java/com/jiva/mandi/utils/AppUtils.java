package com.jiva.mandi.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public final class AppUtils {
    private static final String TAG = "AppUtils";

    /**
     * @param e exception that will be recorded in crash analytics
     */
    public static void handleException(Throwable e) {
        Log.e(TAG, e.getMessage());
    }

    /**
     * @param context      View context.
     * @param jsonFileName json file name.
     * @return Json string from json file which is stored in asset folder.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String loadJSONFromAsset(Context context, String jsonFileName) {
        if (context == null) return "";
        if (TextUtils.isEmpty(jsonFileName)) return "";

        try {
            AssetManager manager = context.getAssets();
            InputStream is = manager.open(jsonFileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            Log.e(TAG, "loadJSONFromAsset: " + exception.getMessage());
        }
        return "";

    }

    /**
     * @param loyaltyId last loyalty id from db.
     * @return return new unique loyaltyId
     */
    public static String getNewLoyaltyId(String loyaltyId) {
        String newLoyaltyId = "S100";
        if (!TextUtils.isEmpty(loyaltyId)) {
            String indexNumber = loyaltyId.substring(1);
            if (TextUtils.isDigitsOnly(indexNumber)) {
                int newIndexNumber = Integer.parseInt(indexNumber) + 1;
                newLoyaltyId = "S" + newIndexNumber;
            }
        }
        return newLoyaltyId;
    }
}
