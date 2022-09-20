package com.jiva.mandi.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;


public class SnackBarUtils {

    public static void showSnackBar(View rootView, String mMessage, int duration) {
        if (rootView == null) return;
        Snackbar.make(rootView, mMessage, duration)
                .setAction("Action", null)
                .show();
    }
}