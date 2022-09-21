package com.jiva.mandi.utils;

import android.text.TextUtils;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

public final class ValidationUtil {

    /**
     * @param viewEditText      Edittext in which error message show
     * @param viewInputTextLout InputTextLayout in which error message show
     * @param message           Error message which we want to show below the edit text
     */
    public static void setErrorIntoInputTextLayout(View viewEditText, View viewInputTextLout, String message) {
        if (!TextUtils.isEmpty(message)) {
            ((TextInputLayout) viewInputTextLout).setError(message);
            ((TextInputLayout) viewInputTextLout).setErrorIconDrawable(0);
            (viewEditText).requestFocus();
        }
    }

    /**
     * @param viewInputTextLout InputTextLayout in which we want to remove the error message
     */
    public static void removeErrorFromTextLayout(TextInputLayout viewInputTextLout) {
        if (viewInputTextLout != null) {
            viewInputTextLout.setError("");
            viewInputTextLout.setErrorEnabled(false);
            viewInputTextLout.setError(null);
        }
    }

    /**
     * @param mobileNumber text entered from the user for mobile number
     * @return boolean to specify the mobile number is valid or not
     */
    public static boolean isMobileNumberIsValid(String mobileNumber) {
        if (mobileNumber == null) return false;

        if (TextUtils.isEmpty(mobileNumber.trim())) return false;

        return !TextUtils.isEmpty(mobileNumber);
    }

    /**
     * @param mobileNumber text entered from the user for mobile number
     * @return boolean to specify the mobile number length is valid or not
     */
    public static boolean isMobileNumberLengthValid(String mobileNumber) {
        if (isMobileNumberIsValid(mobileNumber)) {
            return mobileNumber.trim().length() == 10;

        }
        return false;
    }


    /**
     * @param password text entered from the user for password
     * @return boolean to specify the password is valid or not
     */
    public static boolean isPasswordIsValid(String password) {
        if (password == null) return false;
        return !TextUtils.isEmpty(password.trim());
    }

    /**
     * @param name text entered from the user for name
     * @return boolean to specify the name is valid or not
     */
    public static boolean isValidInput(String name) {
        if (name == null) return false;
        return !TextUtils.isEmpty(name.trim());
    }

    /**
     * @param weight text entered from the user for name
     * @return boolean to specify the name is valid or not
     */
    public static boolean isWightIsValid(String weight) {
        if (weight == null) return false;
        if (TextUtils.isEmpty(weight.trim())) return false;
        if (!isNumeric(weight)) return false;
        return !(Double.parseDouble(weight) <= 0);
    }

    /**
     * @param strNum string to check is numeric or not
     * @return true/false based on input parameter
     */
    public static boolean isNumeric(String strNum) {
        if (TextUtils.isEmpty(strNum)) return false;
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean checkMinTextValidation(String text, int textLength) {
        if (TextUtils.isEmpty(text)) {
            return false;
        } else return text.trim().length() >= textLength;
    }


}
