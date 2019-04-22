package com.anote.forms.pin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anote.R;
import com.anote.db.sql.PinSQL;
import com.anote.displays.selectionPage.NoteSelectionActivity;
import com.anote.common.util.ToastPrinter;

public class SaveButton implements View.OnClickListener {

    public static final String SAVED = "Saved";
    public static final String DUPLICATE = "This key already exists!";
    public static final String EMPTY_KEY = "Enter a key!";

    private Button button;
    private UserInput userInput;
    private Activity activity;
    private PinSQL pinSql;
    private ToastPrinter toastPrinter;

    private boolean editingPin;
    private String keyToBeEdited;

    public SaveButton(Activity activity, UserInput userInput,
                      PinSQL pinSql, ToastPrinter toastPrinter) {
        this.userInput = userInput;
        this.pinSql = pinSql;
        this.activity = activity;
        this.toastPrinter = toastPrinter;
        this.button = activity.findViewById(R.id.save_pin_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        userInput.find();
        Context context = view.getContext();

        if (!userInput.areValid()) {
            keyErrorNotification(context, EMPTY_KEY);
            return;
        }

        if (!editingPin) {
            insertNewPin(context);
        }
        else {
            updateExistingPin(context);
        }
    }

    public void editOnly(String key) {
        this.keyToBeEdited = key;
        this.editingPin = true;
    }

    private void insertNewPin(Context context) {
        if (pinSql.RECORD_EXISTS(userInput.getEnteredKey())) {
            keyErrorNotification(context, DUPLICATE);
        }
        else {
            pinSql.INSERT(userInput);
            goBackToNoteSelection(context);
        }
    }

    private void updateExistingPin(Context context) {
        String enteredKey = userInput.getEnteredKey();
        if (!keyToBeEdited.equals(enteredKey) && pinSql.RECORD_EXISTS(enteredKey)) {
            keyErrorNotification(context, DUPLICATE);
        }
        else {
            pinSql.UPDATE(userInput, keyToBeEdited);
            goBackToNoteSelection(context);
        }
    }

    private void keyErrorNotification(Context context, String error) {
        toastPrinter.print(context, error, Toast.LENGTH_SHORT);
        TextView keyText = activity.findViewById(R.id.key_text);
        keyText.setTextColor(Color.parseColor("#8b0000"));
    }

    private void goBackToNoteSelection(Context context) {
        toastPrinter.print(context, SAVED, Toast.LENGTH_SHORT);
        Intent intent = new Intent(context, NoteSelectionActivity.class);
        context.startActivity(intent);
    }

}
