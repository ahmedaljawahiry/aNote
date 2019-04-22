package com.anote.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.anote.R;
import com.anote.common.abstractActivites.ANoteActivity;
import com.anote.common.util.ToastPrinter;
import com.anote.displays.selectionPage.NoteSelectionActivity;


public class LoginActivity extends ANoteActivity {

    public static final String UNSUPPORTED_PROMPT = "Unsupported phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = new Intent(this, NoteSelectionActivity.class);

        CompatibilityChecker compatibilityChecker = new CompatibilityChecker(this);
        BiometricAuth biometricAuth = new BiometricAuth(this, intent);

        if (compatibilityChecker.isCompatible()) {
            new AuthenticateButton(this, biometricAuth);
            biometricAuth.authenticateUser();
        }
        else {
            new ToastPrinter().print(this, UNSUPPORTED_PROMPT, Toast.LENGTH_LONG);
        }
    }
}

