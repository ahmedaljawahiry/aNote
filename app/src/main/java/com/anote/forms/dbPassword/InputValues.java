package com.anote.forms.dbPassword;

import android.app.Activity;

import com.anote.R;
import com.anote.common.userInput.FormInputValues;

import java.util.HashMap;
import java.util.Map;

public class InputValues extends FormInputValues {

    private Activity activity;
    private String password;
    private String confirmationPassword;

    public InputValues(Activity activity) {
        this.activity = activity;
        find();
    }

    public void find() {
        this.password = convertEditTextToString(
                activity.findViewById(R.id.entered_password)
        );
        this.confirmationPassword = convertEditTextToString(
                activity.findViewById(R.id.entered_password_confirmation)
        );
    }

    public boolean areValid() {
        if (password.isEmpty()
                || confirmationPassword.isEmpty()
                || !password.equals(confirmationPassword)) {
            return false;
        }
        return true;
    }

    public boolean nothingEntered() {
        if (password.isEmpty() && confirmationPassword.isEmpty()) {
            return true;
        }
        return false;
    }

    public Map<String, Object> getDbValueMap() {
        return new HashMap<>();
    }

    public String getPassword() {
        return password;
    }
}
