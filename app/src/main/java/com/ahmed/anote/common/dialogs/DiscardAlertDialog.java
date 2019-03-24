package com.ahmed.anote.common.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;

public class DiscardAlertDialog {

    public static final String ALERT_TITLE = "Discard Changes";
    public static final String ALERT_MESSAGE = "Are you sure?";
    public static final String ALERT_POSITIVE = "YES";
    public static final String ALERT_NEGATIVE = "NO";

    private AlertDialog alertDialog;
    private Activity activity;

    public DiscardAlertDialog(Activity activity) {
        this.activity = activity;
        this.alertDialog = new AlertDialog.Builder(activity).setTitle(ALERT_TITLE)
                .setMessage(ALERT_MESSAGE)
                .setPositiveButton(ALERT_POSITIVE,
                        (dialog, which) -> positiveAction(dialog))
                .setNegativeButton(ALERT_NEGATIVE,
                        (dialog, which) -> dialog.dismiss())
                .create();
    }

    public void show() {
        alertDialog.show();
    }

    private void positiveAction(DialogInterface dialog) {
        dialog.dismiss();
        Intent intent = new Intent(activity, NoteSelectionActivity.class);
        activity.startActivity(intent);
    }

}
