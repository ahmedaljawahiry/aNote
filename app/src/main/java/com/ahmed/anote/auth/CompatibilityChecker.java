package com.ahmed.anote.auth;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.Manifest;
import android.support.v4.app.ActivityCompat;

import static android.content.Context.KEYGUARD_SERVICE;

public class CompatibilityChecker {

    private Activity activity;
    private KeyguardManager keyguardManager;
    private PackageManager packageManager;

    public CompatibilityChecker(Activity activity) {
        this.activity = activity;
        this.keyguardManager = (KeyguardManager) activity.getSystemService(KEYGUARD_SERVICE);
        this.packageManager = activity.getPackageManager();
    }

    public Boolean isCompatible() {

        if (!keyguardManager.isKeyguardSecure()
                || !packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
                || !permissionGranted()) {
            return false;
        }

        return true;
    }

    private boolean permissionGranted() {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.USE_BIOMETRIC)
                == PackageManager.PERMISSION_GRANTED;
    }

}
