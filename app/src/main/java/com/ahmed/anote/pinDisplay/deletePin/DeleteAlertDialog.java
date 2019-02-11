package com.ahmed.anote.pinDisplay.deletePin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.ahmed.anote.db.SQL;
import com.ahmed.anote.noteSelection.NoteSelectionActivity;
import com.ahmed.anote.pinDisplay.PinDisplayActivity;

public class DeleteAlertDialog {

    public static final String ALERT_TITLE = "Delete Pin";
    public static final String ALERT_MESSAGE = "Are you sure?";
    public static final String ALERT_POSITIVE = "YES";
    public static final String ALERT_NEGATIVE = "NO";

    private AlertDialog alertDialog;

    public DeleteAlertDialog(PinDisplayActivity activity, SQL sql) {
        if (sql.dbIsReadOnly()) {
            throw new IllegalArgumentException("Input DB must be writable!");
        }

        alertDialog = new AlertDialog.Builder(activity).setTitle(ALERT_TITLE)
                .setMessage(ALERT_MESSAGE)
                .setPositiveButton(ALERT_POSITIVE,
                        (dialog, which) -> positiveAction(sql, activity, dialog))
                .setNegativeButton(ALERT_NEGATIVE,
                        (dialog, which) -> dialog.dismiss())
                .create();
    }

    public void show() {
        alertDialog.show();
    }

    private void positiveAction(SQL sql, PinDisplayActivity activity,
                                DialogInterface dialog) {
        sql.DELETE_PIN(activity.getValues().getKey(), activity.getValues().getPin());
        dialog.dismiss();
        Intent intent = new Intent(activity, NoteSelectionActivity.class);
        activity.startActivity(intent);
    }
}
