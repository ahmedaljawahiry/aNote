package com.ahmed.anote.pinCreation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahmed.anote.R;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.util.ToastPrinter;

public class CreatePinNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin_note);

        new SaveButton(this, new EnteredValues(this), new DbHelper(this), new ToastPrinter());
    }
}
