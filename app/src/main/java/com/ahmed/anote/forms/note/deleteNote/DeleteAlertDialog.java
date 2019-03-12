package com.ahmed.anote.forms.note.deleteNote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.ahmed.anote.db.sql.NoteSQL;
import com.ahmed.anote.db.sql.PinSQL;
import com.ahmed.anote.displays.pinDisplay.PinDisplayActivity;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;
import com.ahmed.anote.forms.note.NoteFormActivity;

public class DeleteAlertDialog {

    public static final String ALERT_TITLE = "Delete Note";
    public static final String ALERT_MESSAGE = "Are you sure?";
    public static final String ALERT_POSITIVE = "YES";
    public static final String ALERT_NEGATIVE = "NO";

    private AlertDialog alertDialog;

    public DeleteAlertDialog(NoteFormActivity activity, NoteSQL noteSql) {
        alertDialog = new AlertDialog.Builder(activity).setTitle(ALERT_TITLE)
                .setMessage(ALERT_MESSAGE)
                .setPositiveButton(ALERT_POSITIVE,
                        (dialog, which) -> positiveAction(noteSql, activity, dialog))
                .setNegativeButton(ALERT_NEGATIVE,
                        (dialog, which) -> dialog.dismiss())
                .create();
    }

    public void show() {
        alertDialog.show();
    }

    private void positiveAction(NoteSQL noteSql, NoteFormActivity activity,
                                DialogInterface dialog) {
        noteSql.DELETE(activity.getNoteTitle());
        dialog.dismiss();
        Intent intent = new Intent(activity, NoteSelectionActivity.class);
        activity.startActivity(intent);
    }
}
