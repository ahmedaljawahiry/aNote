package com.ahmed.anote.login;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.Manifest;
import android.support.v4.app.ActivityCompat;

import static android.content.Context.KEYGUARD_SERVICE;

public class CompatibilityChecker {

    private Activity activity;
    private KeyguardManager keyguardManager;

    public CompatibilityChecker(Activity activity) {
        this.activity = activity;
        this.keyguardManager = (KeyguardManager) activity.getSystemService(KEYGUARD_SERVICE);
    }

    public Boolean isCompatible() {

        PackageManager packageManager = activity.getPackageManager();

        if (!keyguardManager.isKeyguardSecure()) {
            return false;
        }

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return true;
        }

        return true;
    }


}
