package com.anote.forms.note;

import android.app.Activity;

import com.anote.R;
import com.anote.db.Contract;
import com.anote.common.userInput.FormInputValues;

import java.util.HashMap;
import java.util.Map;

public class InputValues extends FormInputValues {

    private Activity activity;
    private String enteredTitle;
    private String enteredNote;
    private boolean locked;
    private Map<String, Object> dbValueMap;

    public InputValues(Activity activity) {
        this.activity = activity;
        this.dbValueMap = new HashMap<>();
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
        return true;
    }

    public Map<String, Object> getDbValueMap() {
        find();
        dbValueMap.put(Contract.Notes.COLUMN_TITLE, enteredTitle);
        dbValueMap.put(Contract.Notes.COLUMN_TEXT, enteredNote);
        dbValueMap.put(Contract.Notes.COLUMN_SECURITY_LEVEL, locked);
        return dbValueMap;
    }

    public boolean nothingEntered() {
        return (enteredNote.isEmpty() && enteredTitle.isEmpty() && !locked);
    }

    public String getEnteredTitle() {
        return enteredTitle;
    }

    public String getEnteredNote() {
        return enteredNote;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
