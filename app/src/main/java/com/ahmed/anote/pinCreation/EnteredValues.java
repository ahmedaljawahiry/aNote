package com.ahmed.anote.pinCreation;

import android.app.Activity;
import android.text.Editable;
import android.widget.EditText;

import com.ahmed.anote.R;


public class EnteredValues {

    private String enteredKey;
    private String enteredHint;
    private String enteredPin;

    public EnteredValues(Activity activity) {
        EditText keyEditText = activity.findViewById(R.id.key);
        this.enteredKey = convertEditTextToString(keyEditText);

        EditText hintEditText = activity.findViewById(R.id.hint);
        this.enteredHint = convertEditTextToString(hintEditText);

        EditText pinEditText = activity.findViewById(R.id.pin);
        this.enteredPin = convertEditTextToString(pinEditText);
    }

    private String convertEditTextToString(EditText refEditText) {
        Editable text = refEditText.getText();
        if (text == null) {
            return "";
        }
        else {
            return text.toString();
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
