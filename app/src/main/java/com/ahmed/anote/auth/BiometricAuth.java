package com.ahmed.anote.auth;

import android.app.Activity;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.widget.Toast;

import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;
import com.ahmed.anote.common.ToastPrinter;

public class BiometricAuth {

    public static final String TITLE = "aNote";
    public static final String SUBTITLE = "Authentication is required to continue.";
    public static final String NEGATIVE_BUTTON_TEXT = "Cancel";
    public static final String CANCELLATION_PROMPT = "Cancelled";

    private Activity activity;

    public BiometricAuth(Activity activity) {
        this.activity = activity;
    }

    public void authenticateUser() {
        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(activity)
                .setTitle(TITLE)
                .setSubtitle(SUBTITLE)
                .setNegativeButton(
                        NEGATIVE_BUTTON_TEXT,
                        activity.getMainExecutor(), (dialog, which) -> {
                            return;
                        })
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
                () -> new ToastPrinter().print(activity, CANCELLATION_PROMPT, Toast.LENGTH_SHORT)
        );
        return cancellationSignal;
    }

}
