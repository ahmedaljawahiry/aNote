package com.ahmed.anote.forms;

import android.widget.EditText;

import java.util.Map;

public abstract class FormInputValues {

    abstract public void find();
    abstract public boolean areValid();
    abstract public boolean nothingEntered();
    abstract public Map<String, String> getDbValueMap();

    protected String convertEditTextToString(EditText editText) {
        if (editText == null || editText.getText() == null) {
            return "";
        }
        else {
            return editText.getText().toString();
        }
    }
}
