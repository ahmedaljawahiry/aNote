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
import com.ahmed.anote.db.Sql;
import com.ahmed.anote.noteSelection.NoteSelectionActivity;
import com.ahmed.anote.util.ToastPrinter;

public class SaveButton implements View.OnClickListener {

    public static final String TOAST_MESSAGE = "Saved";

    private Button button;
    private EnteredValues enteredValues;
    private DbHelper dbHelper;
    private ToastPrinter toastPrinter;

    public SaveButton(Activity activity, EnteredValues enteredValues,
                      DbHelper dbHelper, ToastPrinter toastPrinter) {
        this.enteredValues = enteredValues;
        this.dbHelper = dbHelper;
        this.toastPrinter = toastPrinter;
        this.button = activity.findViewById(R.id.save_pin_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        enteredValues.find();
        Context context = view.getContext();
        Sql.INSERT_PIN(dbHelper.getWritableDatabase(), enteredValues);

        toastPrinter.print(context, TOAST_MESSAGE, Toast.LENGTH_LONG);
        Intent intent = new Intent(context, NoteSelectionActivity.class);
        context.startActivity(intent);
    }


}
