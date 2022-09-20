package com.jiva.mandi.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.jiva.mandi.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public final class AppUtils {
    private static String TAG = "AppUtils";

    private AppUtils() {
        // This utility class is not publicly instantiable
    }

    /**
     * @param e exception that will be recorded in crash analytics
     */
    public static void handleException(Throwable e) {
        Log.e(TAG, e.getMessage());
        //FirebaseCrashlytics.getInstance().recordException(e);
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) {
        try {
            AssetManager manager = context.getAssets();
            InputStream is = manager.open(jsonFileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            return new String(buffer, "UTF-8");
        } catch (IOException exception) {
            Log.e(TAG, "loadJSONFromAsset: " + exception.getMessage());
        }
        return "";

    }

    /**
     * @param context View context
     * @param message error message to show as toast
     */
    public static void showShortToast(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

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
