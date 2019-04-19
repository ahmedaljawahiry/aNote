package com.ahmed.anote.forms.note;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Intent;
import android.database.Cursor;
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
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;

public class NoteFormActivity extends DbRecordDeleter implements LifecycleObserver {

    private DbHelper dbHelper;
    private Bundle bundle;
    private DiscardAlertDialog discardAlertDialog;
    private UserInput userInput;

    private String noteTitle;
    private String noteBody;
    private boolean locked;
    private boolean isExistingNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        userInput = new UserInput(this);
        discardAlertDialog = new DiscardAlertDialog(this);
        dbHelper = DbHelper.getInstance(this);
        bundle = this.getIntent().getExtras();

        SaveButton saveButton = new SaveButton(
                this,
                userInput,
                new NoteSQL(dbHelper.getWritableDatabase()),
                new ToastPrinter());

        fillPageWithExistingValues(saveButton);

        new LockButton(this, userInput, locked);

        new DeleteButton(
                this,
                new DeleteAlertDialog(
                        this,
                        new NoteSQL(dbHelper.getWritableDatabase()),
                        "Delete Note"),
                discardAlertDialog,
                !isExistingNote);
    }

    private void fillPageWithExistingValues(SaveButton saveButton) {
        if (bundle != null) {
            isExistingNote = true;

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            noteTitle = bundle.getString(Contract.Notes.COLUMN_TITLE);

            try(Cursor noteCursor = new NoteSQL(db).GET_NOTE_FROM_PK(noteTitle)) {
                noteBody = noteCursor.getString(
                        noteCursor.getColumnIndex(Contract.Notes.COLUMN_TEXT)
                );
                locked = (noteCursor.getInt(
                        noteCursor.getColumnIndex(Contract.Notes.COLUMN_SECURITY_LEVEL)) != 0
                );
            }


            TextEditor.enter(this, R.id.entered_title, noteTitle, true);
            TextEditor.enter(this, R.id.entered_note, noteBody, true);

            saveButton.editOnly(noteTitle);
        }
    }

    @Override
    public void onBackPressed() {
        userInput.find();
        boolean newEmptyNote = !isExistingNote && !userInput.nothingEntered();
        boolean existingUnchangedNote =
                isExistingNote && (
                        !userInput.getEnteredTitle().equals(noteTitle) ||
                        !userInput.getEnteredNote().equals(noteBody) ||
                        userInput.isLocked() != locked
                );

        if (newEmptyNote || existingUnchangedNote) {
            discardAlertDialog.show();
        }
        else {
            super.onBackPressed();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onMoveToBackground() {
        if (locked) {
            this.finish();
        }
    }

    @Override
    public String getPK() {
        return noteTitle;
    }
}
