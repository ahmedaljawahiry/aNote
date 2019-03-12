package com.ahmed.anote.forms.pin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.db.sql.PinSQL;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;
import com.ahmed.anote.util.ToastPrinter;

public class SaveButton implements View.OnClickListener {

    public static final String SAVED = "Saved";
    public static final String DUPLICATE = "This key already exists!";
    public static final String EMPTY_KEY = "Enter a key!";

    private Button button;
    private EnteredValues enteredValues;
    private Activity activity;
    private PinSQL pinSql;
    private ToastPrinter toastPrinter;

    private boolean editingPin;
    private String keyToBeEdited;

    public SaveButton(Activity activity, EnteredValues enteredValues,
                      PinSQL pinSql, ToastPrinter toastPrinter) {
        this.enteredValues = enteredValues;
        this.pinSql = pinSql;
        this.activity = activity;
        this.toastPrinter = toastPrinter;
        this.button = activity.findViewById(R.id.save_pin_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        enteredValues.find();
        Context context = view.getContext();

        if (!enteredValues.areValid()) {
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
        if (pinSql.KEY_EXISTS(enteredValues.getEnteredKey())) {
            keyErrorNotification(context, DUPLICATE);
        }
        else {
            pinSql.INSERT(enteredValues);
            goBackToNoteSelection(context);
        }
    }

    private void updateExistingPin(Context context) {
        String enteredKey = enteredValues.getEnteredKey();
        if (!keyToBeEdited.equals(enteredKey) && pinSql.KEY_EXISTS(enteredKey)) {
            keyErrorNotification(context, DUPLICATE);
        }
        else {
            pinSql.UPDATE(enteredValues, keyToBeEdited);
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
