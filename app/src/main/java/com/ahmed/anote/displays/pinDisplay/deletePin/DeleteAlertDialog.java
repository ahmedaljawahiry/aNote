package com.ahmed.anote.displays.pinDisplay.deletePin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.ahmed.anote.db.sql.PinSQL;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;
import com.ahmed.anote.displays.pinDisplay.PinDisplayActivity;

public class DeleteAlertDialog {

    public static final String ALERT_TITLE = "Delete Pin";
    public static final String ALERT_MESSAGE = "Are you sure?";
    public static final String ALERT_POSITIVE = "YES";
    public static final String ALERT_NEGATIVE = "NO";

    private AlertDialog alertDialog;

    public DeleteAlertDialog(PinDisplayActivity activity, PinSQL pinSql) {
        alertDialog = new AlertDialog.Builder(activity).setTitle(ALERT_TITLE)
                .setMessage(ALERT_MESSAGE)
                .setPositiveButton(ALERT_POSITIVE,
                        (dialog, which) -> positiveAction(pinSql, activity, dialog))
                .setNegativeButton(ALERT_NEGATIVE,
                        (dialog, which) -> dialog.dismiss())
                .create();
    }

    public void show() {
        alertDialog.show();
    }

    private void positiveAction(PinSQL pinSql, PinDisplayActivity activity,
                                DialogInterface dialog) {
        pinSql.DELETE(activity.getValues().getKey(), activity.getValues().getPin());
        dialog.dismiss();
        Intent intent = new Intent(activity, NoteSelectionActivity.class);
        activity.startActivity(intent);
    }
}
