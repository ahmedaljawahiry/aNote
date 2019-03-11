package com.ahmed.anote.login;

import android.app.Activity;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Toast;

import com.ahmed.anote.noteSelection.NoteSelectionActivity;
import com.ahmed.anote.util.ToastPrinter;

public class BiometricAuth {

    private Activity activity;

    public BiometricAuth(Activity activity) {
        this.activity = activity;
    }

    public void authenticateUser(View view) {
        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(activity)
                .setTitle("aNote")
                .setSubtitle("Authentication is required to continue.")
                .setNegativeButton(
                        "Cancel",
                        activity.getMainExecutor(),
                        (dialog, which) -> new ToastPrinter().print(
                                view.getContext(), "Cancelled.", Toast.LENGTH_SHORT))
                .build();

        biometricPrompt.authenticate(
                getCancellationSignal(),
                activity.getMainExecutor(),
                getAuthenticationCallback()
        );
    }

    private BiometricPrompt.AuthenticationCallback getAuthenticationCallback() {
        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                new ToastPrinter().print(activity, "Authentication Error.", Toast.LENGTH_SHORT);
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Intent intent = new Intent(activity, NoteSelectionActivity.class);
                activity.startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        };
    }

    private CancellationSignal getCancellationSignal() {
        CancellationSignal cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(
                () -> new ToastPrinter().print(activity, "Cancelled.", Toast.LENGTH_SHORT)
        );
        return cancellationSignal;
    }

}
