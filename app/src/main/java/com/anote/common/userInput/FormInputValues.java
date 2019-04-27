package com.anote.common.userInput;

import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Map;

public abstract class FormInputValues {

    abstract public void find();
    abstract public boolean areValid();
    abstract public boolean nothingEntered();
    abstract public Map<String, Object> getDbValueMap();

    protected String convertEditTextToString(EditText editText) {
        if (editText == null || editText.getText() == null) {
            return "";
        }
        else {
            return editText.getText().toString();
        }
    }

    protected Boolean convertCheckboxToBoolean(CheckBox checkBox) {
        if (checkBox == null) {
            return false;
        }
        else {
            return checkBox.isChecked();
        }
    }
}
