package com.ahmed.anote.forms.note;

import android.app.Activity;
import android.widget.EditText;

import com.ahmed.anote.R;

public class EnteredValues {

    private Activity activity;
    private String enteredTitle;
    private String enteredNote;
    private boolean empty;

    public EnteredValues(Activity activity) {
        this.activity = activity;
        find();
    }

    public void find() {
        this.enteredTitle = convertEditTextToString(activity.findViewById(R.id.entered_title));
        this.enteredNote = convertEditTextToString(activity.findViewById(R.id.entered_note));
    }

    public boolean areValid() {
        if (enteredTitle.isEmpty() || enteredTitle == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isEmpty() {
        return (enteredNote.isEmpty() && enteredTitle.isEmpty());
    }

    private String convertEditTextToString(EditText editText) {
        if (editText == null || editText.getText() == null) {
            return "";
        }
        else {
            return editText.getText().toString();
        }
    }

    public String getEnteredTitle() {
        return enteredTitle;
    }

    public String getEnteredNote() {
        return enteredNote;
    }

}
