package com.ahmed.anote.forms.note;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.ahmed.anote.R;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.db.sql.NoteSQL;
import com.ahmed.anote.forms.note.deleteNote.DeleteAlertDialog;
import com.ahmed.anote.forms.note.deleteNote.DeleteButton;
import com.ahmed.anote.util.NoteAttributes;
import com.ahmed.anote.util.TextEditor;
import com.ahmed.anote.util.ToastPrinter;
import com.ahmed.anote.util.Util;

public class NoteFormActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private DiscardAlertDialog discardAlertDialog;
    private EnteredValues enteredValues;

    private String noteTitle;
    private String noteBody;
    private boolean isExistingNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);
        Util.setSecureFlags(this);

        enteredValues = new EnteredValues(this);
        discardAlertDialog = new DiscardAlertDialog(this);
        dbHelper = new DbHelper(this);
        SaveButton saveButton = new SaveButton(this,
                new EnteredValues(this),
                new NoteSQL(dbHelper.getWritableDatabase()),
                new ToastPrinter());

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            isExistingNote = true;

            dbHelper = new DbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            noteTitle = bundle.getString(NoteAttributes.TITLE.name());
            noteBody = new NoteSQL(db).GET_NOTE_FROM_TITLE(noteTitle);

            saveButton.editOnly(noteTitle);
            TextEditor.enter(this, R.id.entered_title, noteTitle);
            TextEditor.enter(this, R.id.entered_note, noteBody);
        }

        new DeleteButton(
                this,
                new DeleteAlertDialog(this, new NoteSQL(dbHelper.getWritableDatabase())),
                discardAlertDialog,
                !isExistingNote);
    }

    @Override
    public void onBackPressed() {
        enteredValues.find();
        boolean newEmptyNote = !isExistingNote && !enteredValues.isEmpty();
        boolean existingUnchangedNote =
                isExistingNote && (
                        !enteredValues.getEnteredTitle().equals(noteTitle) ||
                        !enteredValues.getEnteredNote().equals(noteBody)
                );

        if (newEmptyNote || existingUnchangedNote) {
            discardAlertDialog.show();
        }
        else {
            super.onBackPressed();
        }
    }

    public String getNoteTitle() {
        return noteTitle;
    }
}
