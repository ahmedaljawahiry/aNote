package com.ahmed.anote.forms.note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.db.sql.NoteSQL;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;
import com.ahmed.anote.util.ToastPrinter;

public class SaveButton implements View.OnClickListener {

    public static final String SAVED = "Saved";
    public static final String EMPTY_TITLE = "Enter a title!";
    public static final String DUPLICATE = "Enter a unique title!";

    private Button button;
    private EnteredValues enteredValues;
    private Activity activity;
    private NoteSQL noteSQL;
    private ToastPrinter toastPrinter;

    private boolean editingNote;
    private String titleOfNoteToBeEdited;

    public SaveButton(Activity activity, EnteredValues enteredValues,
                      NoteSQL noteSQL, ToastPrinter toastPrinter) {
        this.enteredValues = enteredValues;
        this.noteSQL = noteSQL;
        this.activity = activity;
        this.toastPrinter = toastPrinter;
        this.button = activity.findViewById(R.id.save_note_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        enteredValues.find();
        Context context = view.getContext();

        if (!enteredValues.areValid()) {
            toastPrinter.print(context, EMPTY_TITLE, Toast.LENGTH_SHORT);
            return;
        }

        if (!editingNote) {
            insertNewNote(context);
        }
        else {
            updateExistingNote(context);
        }
    }

    public void editOnly(String title) {
        this.titleOfNoteToBeEdited = title;
        this.editingNote = true;
    }

    private void insertNewNote(Context context) {
        if (noteSQL.TITLE_EXISTS(enteredValues.getEnteredTitle())) {
            toastPrinter.print(context, DUPLICATE, Toast.LENGTH_SHORT);
        }
        else {
            noteSQL.INSERT(enteredValues);
            goBackToNoteSelection(context);
        }
    }

    private void updateExistingNote(Context context) {
        String enteredTitle = enteredValues.getEnteredTitle();
        if (!titleOfNoteToBeEdited.equals(enteredTitle) && noteSQL.TITLE_EXISTS(enteredTitle)) {
            toastPrinter.print(context, DUPLICATE, Toast.LENGTH_SHORT);
        }
        else {
            noteSQL.UPDATE(enteredValues, titleOfNoteToBeEdited);
            goBackToNoteSelection(context);
        }
    }

    private void goBackToNoteSelection(Context context) {
        toastPrinter.print(context, SAVED, Toast.LENGTH_SHORT);
        Intent intent = new Intent(context, NoteSelectionActivity.class);
        context.startActivity(intent);
    }
}
