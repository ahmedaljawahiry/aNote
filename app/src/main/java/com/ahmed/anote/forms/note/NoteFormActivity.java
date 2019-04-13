package com.ahmed.anote.forms.note;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.ahmed.anote.R;
import com.ahmed.anote.common.abstractActivites.DbRecordDeleter;
import com.ahmed.anote.common.dialogs.DeleteAlertDialog;
import com.ahmed.anote.common.dialogs.DiscardAlertDialog;
import com.ahmed.anote.db.Contract;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.db.sql.NoteSQL;
import com.ahmed.anote.common.util.TextEditor;
import com.ahmed.anote.common.util.ToastPrinter;

public class NoteFormActivity extends DbRecordDeleter {

    private DbHelper dbHelper;
    private DiscardAlertDialog discardAlertDialog;
    private UserInput userInput;

    private String noteTitle;
    private String noteBody;
    private boolean isExistingNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);

        userInput = new UserInput(this);
        discardAlertDialog = new DiscardAlertDialog(this);
        dbHelper = DbHelper.getInstance(this);
        SaveButton saveButton = new SaveButton(this,
                new UserInput(this),
                new NoteSQL(dbHelper.getWritableDatabase()),
                new ToastPrinter());

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            isExistingNote = true;

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            noteTitle = bundle.getString(Contract.Notes.COLUMN_TITLE);
            noteBody = new NoteSQL(db).GET_NOTE_FROM_PK(noteTitle);

            saveButton.editOnly(noteTitle);
            TextEditor.enter(this, R.id.entered_title, noteTitle, true);
            TextEditor.enter(this, R.id.entered_note, noteBody, true);
        }

        new DeleteButton(
                this,
                new DeleteAlertDialog(
                        this,
                        new NoteSQL(dbHelper.getWritableDatabase()),
                        "Delete Note"),
                discardAlertDialog,
                !isExistingNote);
    }

    @Override
    public void onBackPressed() {
        userInput.find();
        boolean newEmptyNote = !isExistingNote && !userInput.nothingEntered();
        boolean existingUnchangedNote =
                isExistingNote && (
                        !userInput.getEnteredTitle().equals(noteTitle) ||
                        !userInput.getEnteredNote().equals(noteBody)
                );

        if (newEmptyNote || existingUnchangedNote) {
            discardAlertDialog.show();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public String getPK() {
        return noteTitle;
    }
}
