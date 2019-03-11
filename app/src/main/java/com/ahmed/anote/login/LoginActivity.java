package com.ahmed.anote.login;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.util.ToastPrinter;


public class LoginActivity extends AppCompatActivity implements LifecycleObserver {

    public static final String UNSUPPORTED_PROMPT = "Unsupported phone.";

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
            new ToastPrinter().print(this, UNSUPPORTED_PROMPT, Toast.LENGTH_SHORT);
        }

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onMoveToBackground() {
        this.finishAffinity();
    }
}

