package com.ahmed.anote.auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.common.ToastPrinter;
import com.ahmed.anote.common.Util;


public class LoginActivity extends AppCompatActivity {

    public static final String UNSUPPORTED_PROMPT = "Unsupported phone.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Util.setSecureFlags(this);

        CompatibilityChecker compatibilityChecker = new CompatibilityChecker(this);
        BiometricAuth biometricAuth = new BiometricAuth(this);

        if (compatibilityChecker.isCompatible()) {
            new AuthenticateButton(this, biometricAuth);
            biometricAuth.authenticateUser();
        }
        else {
            new ToastPrinter().print(this, UNSUPPORTED_PROMPT, Toast.LENGTH_SHORT);
        }
    }
}

