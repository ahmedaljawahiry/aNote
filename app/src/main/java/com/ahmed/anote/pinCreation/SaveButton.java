package com.ahmed.anote.pinCreation;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.db.Contract;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.noteSelection.NoteSelectionActivity;
import com.ahmed.anote.util.ToastPrinter;

public class SaveButton implements View.OnClickListener {

    private Activity activity;
    private Button button;
    private EnteredValues enteredValues;

    public SaveButton(Activity activity) {
        this.activity = activity;

        this.button = activity.findViewById(R.id.save_pin_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.enteredValues = new EnteredValues(activity);
        Context context = view.getContext();
        saveToDb(context);

        new ToastPrinter().print(context, "Saved.", Toast.LENGTH_LONG);
        Intent intent = new Intent(context, NoteSelectionActivity.class);
        context.startActivity(intent);
    }

    private void saveToDb(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.Pins.COLUMN_KEY, enteredValues.getEnteredKey());
        values.put(Contract.Pins.COLUMN_PIN, enteredValues.getEnteredPin());
        values.put(Contract.Pins.COLUMN_HINT, enteredValues.getEnteredHint());

        db.insert(Contract.Pins.TABLE_NAME, null, values);
    }
}
