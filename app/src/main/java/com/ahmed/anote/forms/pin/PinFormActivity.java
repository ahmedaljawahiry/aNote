package com.ahmed.anote.forms.pin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahmed.anote.R;
import com.ahmed.anote.common.dialogs.DiscardAlertDialog;
import com.ahmed.anote.db.Contract;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.db.sql.PinSQL;
import com.ahmed.anote.common.util.TextEditor;
import com.ahmed.anote.common.util.ToastPrinter;
import com.ahmed.anote.common.util.Util;

public class PinFormActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private UserInput userInput;
    private DiscardAlertDialog discardAlertDialog;
    private String existingKey;
    private String existingHint;
    private String existingPin;
    private boolean isExistingPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_form);
        Util.setSecureFlags(this);

        userInput = new UserInput(this);
        discardAlertDialog = new DiscardAlertDialog(this);
        dbHelper = DbHelper.getInstance(this);
        SaveButton saveButton = new SaveButton(this,
                userInput,
                new PinSQL(dbHelper.getWritableDatabase()),
                new ToastPrinter());

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            isExistingPin = true;

            existingKey = bundle.getString(Contract.Pins.COLUMN_KEY);
            existingHint = bundle.getString(Contract.Pins.COLUMN_HINT);
            existingPin = bundle.getString(Contract.Pins.COLUMN_PIN);

            saveButton.editOnly(existingKey);
            TextEditor.enter(this, R.id.entered_key, existingKey, true);
            TextEditor.enter(this, R.id.entered_hint, existingHint, true);
            TextEditor.enter(this, R.id.entered_pin, existingPin, true);
        }
    }

    @Override
    public void onBackPressed() {
        userInput.find();
        boolean newEmptyPin = !isExistingPin && !userInput.nothingEntered();
        boolean existingUnchangedPin =
                isExistingPin && (
                        !userInput.getEnteredKey().equals(existingKey) ||
                                !userInput.getEnteredHint().equals(existingHint) ||
                                !userInput.getEnteredPin().equals(existingPin)
                );

        if (newEmptyPin || existingUnchangedPin) {
            discardAlertDialog.show();
        }
        else {
            super.onBackPressed();
        }
    }
}
