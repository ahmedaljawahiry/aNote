package com.ahmed.anote.forms.pin;

import android.os.Bundle;
import android.widget.CheckBox;

import com.ahmed.anote.R;
import com.ahmed.anote.common.abstractActivites.ANoteActivity;
import com.ahmed.anote.common.dialogs.DiscardAlertDialog;
import com.ahmed.anote.db.Contract;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.db.sql.PinSQL;
import com.ahmed.anote.common.util.TextEditor;
import com.ahmed.anote.common.util.ToastPrinter;

public class PinFormActivity extends ANoteActivity {

    private DbHelper dbHelper;
    private UserInput userInput;
    private DiscardAlertDialog discardAlertDialog;
    private String existingKey;
    private String existingHint;
    private String existingPin;
    private boolean existingCheckboxTicked;
    private boolean isExistingPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_form);

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
            existingCheckboxTicked = (bundle.getInt(Contract.Pins.COLUMN_SECURITY_LEVEL) != 0);

            saveButton.editOnly(existingKey);
            TextEditor.enter(this, R.id.entered_key, existingKey, true);
            TextEditor.enter(this, R.id.entered_hint, existingHint, true);
            TextEditor.enter(this, R.id.entered_pin, existingPin, true);
            ((CheckBox) this.findViewById(R.id.pin_locked_checkbox))
                    .setChecked(existingCheckboxTicked);
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
