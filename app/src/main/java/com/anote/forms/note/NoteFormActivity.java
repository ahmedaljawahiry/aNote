package com.anote.forms.note;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.os.Bundle;

import com.anote.R;
import com.anote.common.activities.DbRecordDeleter;
import com.anote.common.dialogs.DeleteAlertDialog;
import com.anote.common.dialogs.DiscardAlertDialog;
import com.anote.db.CipherDb;
import com.anote.db.Contract;
import com.anote.db.sql.NoteSQL;
import com.anote.common.util.TextEditor;
import com.anote.common.util.ToastPrinter;

import net.sqlcipher.Cursor;

public class NoteFormActivity extends DbRecordDeleter implements LifecycleObserver {

    private CipherDb cipherDb;
    private Bundle bundle;
    private DiscardAlertDialog discardAlertDialog;
    private InputValues inputValues;

    private String noteTitle;
    private String noteBody;
    private boolean locked;
    private boolean isExistingNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        this.getSupportActionBar().hide();

        cipherDb = getCipherDb();
        inputValues = new InputValues(this);
        discardAlertDialog = new DiscardAlertDialog(this);
        bundle = this.getIntent().getExtras();

        SaveButton saveButton = new SaveButton(
                this,
                inputValues,
                new NoteSQL(cipherDb),
                new ToastPrinter());

        fillPageWithExistingValues(saveButton);

        new LockButton(this, inputValues, locked);

        new DeleteButton(
                this,
                new DeleteAlertDialog(
                        this,
                        new NoteSQL(cipherDb),
                        "Delete Note"),
                discardAlertDialog,
                !isExistingNote);
    }

    private void fillPageWithExistingValues(SaveButton saveButton) {
        if (bundle != null) {
            isExistingNote = true;

            noteTitle = bundle.getString(Contract.Notes.COLUMN_TITLE);

            try(Cursor noteCursor = new NoteSQL(cipherDb).GET_NOTE_FROM_PK(noteTitle)) {
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
        inputValues.find();
        boolean newEmptyNote = !isExistingNote && !inputValues.nothingEntered();
        boolean existingUnchangedNote =
                isExistingNote && (
                        !inputValues.getEnteredTitle().equals(noteTitle) ||
                        !inputValues.getEnteredNote().equals(noteBody) ||
                        inputValues.isLocked() != locked
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
