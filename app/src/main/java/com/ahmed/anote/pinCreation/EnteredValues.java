package com.ahmed.anote.pinCreation;

import android.app.Activity;
import android.text.Editable;
import android.widget.EditText;

import com.ahmed.anote.R;


public class EnteredValues {

    private Activity activity;
    private String enteredKey;
    private String enteredHint;
    private String enteredPin;

    public EnteredValues(Activity activity) {
        this.activity = activity;
        find();
    }

    public void find() {
        this.enteredKey = convertEditTextToString(activity.findViewById(R.id.key));
        this.enteredHint = convertEditTextToString(activity.findViewById(R.id.hint));
        this.enteredPin = convertEditTextToString(activity.findViewById(R.id.pin));
    }

    private String convertEditTextToString(EditText editText) {
        if (editText == null || editText.getText() == null) {
            return "";
        }
        else {
            return editText.getText().toString();
        }
    }

    public String getEnteredKey() {
        return enteredKey;
    }

    public String getEnteredHint() {
        return enteredHint;
    }

    public String getEnteredPin() {
        return enteredPin;
    }

}
