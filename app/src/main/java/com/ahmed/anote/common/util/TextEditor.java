package com.ahmed.anote.common.util;

import android.app.Activity;
import android.widget.TextView;

public class TextEditor {

    public static void enter(Activity activity, int viewId, String text, boolean enabled) {
        TextView textView = activity.findViewById(viewId);
        textView.setText(text, TextView.BufferType.NORMAL);
        textView.setEnabled(enabled);
    }
}
