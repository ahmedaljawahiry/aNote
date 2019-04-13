package com.ahmed.anote.displays.pinDisplay;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.ahmed.anote.R;
import com.ahmed.anote.common.abstractActivites.DbRecordDeleter;
import com.ahmed.anote.common.dialogs.DeleteAlertDialog;
import com.ahmed.anote.db.Contract;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.db.sql.PinSQL;
import com.ahmed.anote.common.util.TextEditor;
import com.ahmed.anote.forms.pin.PinFormActivity;

public class PinDisplayActivity extends DbRecordDeleter {

    private PinValues values;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_display);

        Bundle bundle = this.getIntent().getExtras();
        values = new PinValues();
        values.setKey(bundle.getString(Contract.Pins.COLUMN_KEY));
        values.setHint(bundle.getString(Contract.Pins.COLUMN_HINT));

        dbHelper = DbHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        values.setPin(new PinSQL(db).GET_NOTE_FROM_PK(values.getKey()));

        TextEditor.enter(this, R.id.key_display_text, values.getKey(), false);
        TextEditor.enter(this, R.id.hint_display_text, values.getHint(), false);
        TextEditor.enter(this, R.id.pin_display_text, values.getPin(), false);

        new DeleteButton(
                this,
                new DeleteAlertDialog(
                        this,
                        new PinSQL(dbHelper.getWritableDatabase()),
                        "Delete Pin")
        );
        new EditButton(
                this,
                values,
                new Intent(
                        this.getBaseContext(),
                        PinFormActivity.class));
    }

    @Override
    public String getPK() {
        return values.getKey();
    }
}
