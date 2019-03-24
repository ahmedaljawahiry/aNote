package com.ahmed.anote.common;

import android.content.Context;
import android.widget.Toast;

public class ToastPrinter {

    public void print(Context context, String text, int length) {
        Toast.makeText(context, text, length).show();
    }
}
