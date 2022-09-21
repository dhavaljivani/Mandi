package com.jiva.mandi.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.google.android.material.color.MaterialColors;
import com.jiva.mandi.R;

public final class AlertDialogHelper {

    private AlertDialogHelper() {
    }

    /**
     * @param context             View context
     * @param dialogTitle         Dialog title ==> example "Alert" , If you don't want to show title
     *                            then pass null
     * @param dialogMessage*      Dialog message ==> example "Are you sure you want remove this
     *                            item?"
     * @param textPositiveButton* Dialog positive button ==> example "Yes"
     * @param textNegativeButton  Dialog negative button ==> example "No" If you don't want to add
     *                            this button then pass null
     * @param isCancelable        Dialog cancelable when user click outside ==> true/false
     * @param buttonClickListener interface implantation in activity or fragment then pass this if
     *                            you don't want click listener then pass null
     * @param dialogIdentifier    dialogIdentifier pass integer use app constant file example ==>
     *                            1,2,3 if you have only one dialog then pass 0
     */
    public static void showDialog(Context context, String dialogTitle, String dialogMessage, String textPositiveButton,
                                  String textNegativeButton, boolean isCancelable, final DialogButtonClickListener buttonClickListener,
                                  final int dialogIdentifier) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(dialogTitle)) {
            alertDialogBuilder.setTitle(dialogTitle);
        }
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder.setCancelable(isCancelable);

        alertDialogBuilder.setPositiveButton(textPositiveButton, (dialog, id1) -> {
            if (buttonClickListener == null) {
                dialog.dismiss();
            } else {
                buttonClickListener.onPositiveButtonClicked(dialogIdentifier);
            }
        });

        if (textNegativeButton != null) {
            alertDialogBuilder.setNegativeButton(textNegativeButton, (dialog, id1) -> {
                if (buttonClickListener == null) {
                    dialog.dismiss();
                } else {
                    buttonClickListener.onNegativeButtonClicked(dialogIdentifier);
                }
            });
        }

        alertDialogBuilder.setCancelable(isCancelable);

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(dialogInterface -> {
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(MaterialColors.getColor(
                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE), androidx.appcompat.R.attr.editTextColor));
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(MaterialColors.getColor(
                    alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE), androidx.appcompat.R.attr.editTextColor));
        });

        if (!((Activity) context).isFinishing()) {
            alertDialog.show();
        }
    }

    /**
     * @param context       View context
     * @param dialogMessage Dialog message
     */
    @SuppressWarnings("unused")
    public static void showDialog(Context context, String dialogMessage) {
        showDialog(context, null, dialogMessage, context.getString(R.string.ok), null, true, null, 0);
    }

    /**
     * @param context             View context
     * @param title               Dialog title ==> example "Alert" , If you don't want to show title
     *                            then pass null
     * @param dialogMessage       Dialog message ==> example "Are you sure you want remove this
     *                            item?"
     * @param buttonClickListener buttonClickListener interface implantation in activity or fragment
     *                            then pass this. If you don't want click listener then pass null
     */
    @SuppressWarnings("unused")
    public static void showDialog(Context context, String title, String dialogMessage,
                                  final DialogButtonClickListener buttonClickListener, boolean isCancelable) {
        showDialog(context, title, dialogMessage, context.getString(R.string.ok), null, isCancelable,
                buttonClickListener, 0);
    }

    public interface DialogButtonClickListener {

        /**
         * @param dialogIdentifier Alert button positive button identifier for multiple dialog in
         *                         single activity
         */
        @SuppressWarnings({"EmptyMethod", "unused"})
        void onPositiveButtonClicked(int dialogIdentifier);

        /**
         * @param dialogIdentifier Alert button negative button identifier for multiple dialog in
         *                         single activity
         */
        @SuppressWarnings({"EmptyMethod", "unused"})
        void onNegativeButtonClicked(int dialogIdentifier);

    }

    /**
     * If you have multiple alert dialog in screen the dialog Identifier used for identify the which
     * dialog button clicked is press .
     */
    public static final class DialogIdentifier {
        public static final int LOGOUT_DIALOG = 1;
        public static final int SELL_DIALOG = 2;
    }

}
