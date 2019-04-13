package com.ahmed.anote.common.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.ahmed.anote.common.abstractActivites.DbRecordDeleter;
import com.ahmed.anote.db.sql.SqlQueries;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;

public class DeleteAlertDialog {
    public final String ALERT_TITLE;
    public static final String ALERT_MESSAGE = "Are you sure?";
    public static final String ALERT_POSITIVE = "YES";
    public static final String ALERT_NEGATIVE = "NO";

    private AlertDialog alertDialog;
    private DbRecordDeleter activity;
    private SqlQueries sql;

    public DeleteAlertDialog(DbRecordDeleter activity, SqlQueries sql, String title) {
        this.ALERT_TITLE = title;
        this.sql = sql;
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
        sql.DELETE(activity.getPK());
        dialog.dismiss();
        Intent intent = new Intent(activity, NoteSelectionActivity.class);
        activity.startActivity(intent);
    }

}
