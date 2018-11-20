package com.ahmed.anote.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.noteSelection.NoteSelectionActivity;
import com.ahmed.anote.util.ToastPrinter;

public class EnterButton implements View.OnClickListener {
    
    private Activity activity;
    private PasswordChecker passwordChecker;
    private ToastPrinter toastPrinter;
    private Button button;

    public EnterButton(Activity activity, PasswordChecker passwordChecker, ToastPrinter toastPrinter) {
        this.activity = activity;
        this.passwordChecker = passwordChecker;
        this.toastPrinter = toastPrinter;

        this.button = activity.findViewById(R.id.enter_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText enteredPassword = activity.findViewById(R.id.password);
        Context context = view.getContext();
        if (passwordChecker.isCorrect(enteredPassword.getText().toString())) {
            Intent intent = new Intent(context, NoteSelectionActivity.class);
            context.startActivity(intent);
        }
        else {
Intent intent = new Intent(context, NoteSelectionActivity.class);
context.startActivity(intent);
            toastPrinter.print(context, "Incorrect Password.", Toast.LENGTH_LONG);
        }
    }
}
