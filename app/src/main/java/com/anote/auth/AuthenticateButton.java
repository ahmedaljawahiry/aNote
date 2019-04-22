package com.anote.auth;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.anote.R;

public class AuthenticateButton implements View.OnClickListener {

    private ImageView button;
    private BiometricAuth biometricAuth;

    public AuthenticateButton(Activity activity, BiometricAuth biometricAuth) {
        this.biometricAuth = biometricAuth;
        this.button = activity.findViewById(R.id.fingerprint_logo);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        biometricAuth.authenticateUser();
    }
}
