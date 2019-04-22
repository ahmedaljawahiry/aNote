package com.anote.displays.pinDisplay;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Intent;
import android.os.Bundle;

import com.anote.R;
import com.anote.common.abstractActivites.DbRecordDeleter;
import com.anote.common.dialogs.DeleteAlertDialog;
import com.anote.db.CipherDb;
import com.anote.db.Contract;
import com.anote.db.sql.PinSQL;
import com.anote.common.util.TextEditor;
import com.anote.forms.pin.PinFormActivity;

import net.sqlcipher.Cursor;

public class PinDisplayActivity extends DbRecordDeleter implements LifecycleObserver {

    private CipherDb cipherDb;
    private Bundle bundle;
    private boolean lockedPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_display);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        cipherDb = CipherDb.getInstance(this);
        bundle = this.getIntent().getExtras();
        fillBundle();
        fillPageWithExistingValues();

        Intent editButtonIntent = new Intent(this, PinFormActivity.class);
        editButtonIntent.putExtras(bundle);
        new EditButton(this, editButtonIntent);

        new DeleteButton(
                this,
                new DeleteAlertDialog(
                        this,
                        new PinSQL(cipherDb),
                        "Delete Pin")
        );
    }

    private void fillBundle() {
        try(Cursor pinCursor = new PinSQL(cipherDb).GET_NOTE_FROM_PK(
                bundle.getString(Contract.Pins.COLUMN_KEY))) {
            bundle.putString(
                    Contract.Pins.COLUMN_PIN,
                    pinCursor.getString(
                            pinCursor.getColumnIndex(Contract.Pins.COLUMN_PIN)));

            int securityLevel = pinCursor.getInt(
                    pinCursor.getColumnIndex(Contract.Pins.COLUMN_SECURITY_LEVEL));
            lockedPin = securityLevel != 0;

            bundle.putInt(
                    Contract.Pins.COLUMN_SECURITY_LEVEL,
                    securityLevel);
        }
    }

    private void fillPageWithExistingValues() {
        TextEditor.enter(
                this, R.id.key_display_text, bundle.getString(Contract.Pins.COLUMN_KEY), false);
        TextEditor.enter(
                this, R.id.hint_display_text, bundle.getString(Contract.Pins.COLUMN_HINT), false);
        TextEditor.enter(
                this, R.id.pin_display_text, bundle.getString(Contract.Pins.COLUMN_PIN), false);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onMoveToBackground() {
        if (lockedPin) {
            this.finish();
        }
    }

    @Override
    public String getPK() {
        return bundle.getString(Contract.Pins.COLUMN_KEY);
    }
}
