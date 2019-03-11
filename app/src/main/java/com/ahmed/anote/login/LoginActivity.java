package com.ahmed.anote.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.util.ToastPrinter;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CompatibilityChecker compatibilityChecker = new CompatibilityChecker(this);
        BiometricAuth biometricAuth = new BiometricAuth(this);


        if (compatibilityChecker.isCompatible()) {
            new AuthenticateButton(this, biometricAuth);
            View rootView = this.getWindow().getDecorView().getRootView();
            biometricAuth.authenticateUser(rootView);
        }
        else {
            new ToastPrinter().print(this, "Unsupported phone.", Toast.LENGTH_SHORT);
        }
    }

}

