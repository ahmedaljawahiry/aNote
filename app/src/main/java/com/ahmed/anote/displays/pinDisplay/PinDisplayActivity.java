package com.ahmed.anote.displays.pinDisplay;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.db.sql.PinSQL;
import com.ahmed.anote.displays.pinDisplay.deletePin.DeleteAlertDialog;
import com.ahmed.anote.displays.pinDisplay.deletePin.DeleteButton;
import com.ahmed.anote.displays.pinDisplay.editPin.EditButton;
import com.ahmed.anote.util.PinAttributes;
import com.ahmed.anote.util.Util;

public class PinDisplayActivity extends AppCompatActivity {

    private PinValues values;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_display);
        Util.setSecureFlags(this);

        Bundle bundle = this.getIntent().getExtras();
        values = new PinValues();
        values.setKey(bundle.getString(PinAttributes.KEY.name()));
        values.setHint(bundle.getString(PinAttributes.HINT.name()));

        dbHelper = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        values.setPin(new PinSQL(db).GET_PIN_FROM_KEY(values.getKey()));

        createTextOnPage(R.id.key_display_text, values.getKey());
        createTextOnPage(R.id.hint_display_text, values.getHint());
        createTextOnPage(R.id.pin_display_text, values.getPin());

        new DeleteButton(this,
                new DeleteAlertDialog(this, new PinSQL(dbHelper.getWritableDatabase()))
        );
        new EditButton(this, values);
    }

    private void createTextOnPage(int viewId, String displayText) {
        TextView text = this.findViewById(viewId);
        text.setText(displayText, TextView.BufferType.NORMAL);
        text.setEnabled(false);
    }

    public PinValues getValues() {
        return values;
    }
}
