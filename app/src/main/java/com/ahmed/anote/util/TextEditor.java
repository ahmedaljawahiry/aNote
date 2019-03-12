package com.ahmed.anote.util;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

public class TextEditor {

    public static void enter(Activity activity, int viewId, String text) {
        EditText editText = activity.findViewById(viewId);
        editText.setText(text, TextView.BufferType.NORMAL);
    }
}
