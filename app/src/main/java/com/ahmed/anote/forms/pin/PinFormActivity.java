package com.ahmed.anote.forms.pin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.ahmed.anote.R;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.db.sql.PinSQL;
import com.ahmed.anote.util.TextEditor;
import com.ahmed.anote.util.PinAttributes;
import com.ahmed.anote.util.ToastPrinter;
import com.ahmed.anote.util.Util;

public class PinFormActivity extends AppCompatActivity {

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_form);
        Util.setSecureFlags(this);

        dbHelper = new DbHelper(this);
        SaveButton saveButton = new SaveButton(this,
                new EnteredValues(this),
                new PinSQL(dbHelper.getWritableDatabase()),
                new ToastPrinter());

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            String key = bundle.getString(PinAttributes.KEY.name());
            String hint = bundle.getString(PinAttributes.HINT.name());
            String pin = bundle.getString(PinAttributes.PIN.name());

            saveButton.editOnly(key);
            TextEditor.enter(this, R.id.entered_key, key);
            TextEditor.enter(this, R.id.entered_hint, hint);
            TextEditor.enter(this, R.id.entered_pin, pin);
        }
    }
}
