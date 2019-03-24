package com.ahmed.anote.util;

import android.app.Activity;
import android.view.WindowManager;

public final class Util {

    public static void setSecureFlags(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );
    }
}
