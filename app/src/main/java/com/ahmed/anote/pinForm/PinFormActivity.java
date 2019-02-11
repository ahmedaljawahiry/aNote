package com.ahmed.anote.pinForm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.db.SQL;
import com.ahmed.anote.util.PinAttributes;
import com.ahmed.anote.util.ToastPrinter;

public class PinFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_form);

        DbHelper dbHelper = new DbHelper(this);
        SaveButton saveButton = new SaveButton(this,
                new EnteredValues(this),
                new SQL(dbHelper.getWritableDatabase()),
                new ToastPrinter());

        Bundle bundle = this.getIntent().getExtras();
        
        if (bundle != null) {
            String key = bundle.getString(PinAttributes.KEY.name());
            String hint = bundle.getString(PinAttributes.HINT.name());
            String pin = bundle.getString(PinAttributes.PIN.name());

            saveButton.editingPin(key);
            createTextOnPage(R.id.entered_key, key);
            createTextOnPage(R.id.entered_hint, hint);
            createTextOnPage(R.id.entered_pin, pin);
        }
    }

    private void createTextOnPage(int viewId, String text) {
        EditText editText = this.findViewById(viewId);
        editText.setText(text, TextView.BufferType.NORMAL);
    }
}
