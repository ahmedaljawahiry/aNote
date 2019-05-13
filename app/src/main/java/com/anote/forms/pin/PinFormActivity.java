package com.anote.forms.pin;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.os.Bundle;
import android.widget.CheckBox;

import com.anote.R;
import com.anote.common.activities.ANoteActivity;
import com.anote.common.dialogs.DiscardAlertDialog;
import com.anote.db.Contract;
import com.anote.db.sql.PinSQL;
import com.anote.common.util.TextEditor;
import com.anote.common.util.ToastPrinter;

public class PinFormActivity extends ANoteActivity implements LifecycleObserver {

    private InputValues inputValues;
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
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        this.getSupportActionBar().hide();

        inputValues = new InputValues(this);
        discardAlertDialog = new DiscardAlertDialog(this);
        SaveButton saveButton = new SaveButton(
                this,
                inputValues,
                new PinSQL(
                        getCipherDb()
                ),
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
        inputValues.find();
        boolean newEmptyPin = !isExistingPin && !inputValues.nothingEntered();
        boolean existingUnchangedPin =
                isExistingPin && (
                        !inputValues.getEnteredKey().equals(existingKey) ||
                                !inputValues.getEnteredHint().equals(existingHint) ||
                                !inputValues.getEnteredPin().equals(existingPin) ||
                                inputValues.isLocked() != existingCheckboxTicked
                );

        if (newEmptyPin || existingUnchangedPin) {
            discardAlertDialog.show();
        }
        else {
            super.onBackPressed();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onMoveToBackground() {
        if (existingCheckboxTicked) {
            this.finish();
        }
    }
}
