package com.ahmed.anote.forms.pin;

import android.app.Activity;

import com.ahmed.anote.R;
import com.ahmed.anote.db.Contract;
import com.ahmed.anote.forms.FormInputValues;

import java.util.HashMap;
import java.util.Map;


public class UserInput extends FormInputValues {

    private Activity activity;
    private String enteredKey;
    private String enteredHint;
    private String enteredPin;
    private boolean locked;
    private Map<String, Object> dbValueMap;

    public UserInput(Activity activity) {
        this.activity = activity;
        this.dbValueMap = new HashMap<>();
        find();
    }

    public void find() {
        this.enteredKey = convertEditTextToString(activity.findViewById(R.id.entered_key));
        this.enteredHint = convertEditTextToString(activity.findViewById(R.id.entered_hint));
        this.enteredPin = convertEditTextToString(activity.findViewById(R.id.entered_pin));
        this.locked = convertCheckboxToBoolean(activity.findViewById(R.id.pin_locked_checkbox));
    }

    public boolean nothingEntered() {
        return (enteredKey.isEmpty() && enteredPin.isEmpty() && enteredHint.isEmpty());
    }

    public boolean areValid() {
        if (enteredKey.isEmpty() || enteredKey == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public Map<String, Object> getDbValueMap() {
        find();
        dbValueMap.put(Contract.Pins.COLUMN_KEY, enteredKey);
        dbValueMap.put(Contract.Pins.COLUMN_HINT, enteredHint);
        dbValueMap.put(Contract.Pins.COLUMN_PIN, enteredPin);
        dbValueMap.put(Contract.Pins.COLUMN_SECURITY_LEVEL, locked);
        return dbValueMap;
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

    public boolean isLocked() {
        return locked;
    }

}
