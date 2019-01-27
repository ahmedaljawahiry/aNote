package com.ahmed.anote.db;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ahmed.anote.pinCreation.EnteredValues;

public class Sql {

    private Sql(){}

    public static final String CREATE_PINS_TABLE =
            "CREATE TABLE " + Contract.Pins.TABLE_NAME + " (" +
             Contract.Pins._ID + " INTEGER PRIMARY KEY," +
             Contract.Pins.COLUMN_PIN + " INTEGER," +
             Contract.Pins.COLUMN_KEY + " TEXT, " +
             Contract.Pins.COLUMN_HINT + " TEXT)";

    public static final String DELETE_PINS_TABLE =
            "DROP TABLE IF EXISTS " + Contract.Pins.TABLE_NAME;

    // TODO: Write TESTS
    public static final void INSERT_PIN(SQLiteDatabase db, EnteredValues enteredValues) {
        if (db.isReadOnly()) {
            throw new SQLException("The input DB is read only!");
        }

        ContentValues values = new ContentValues();
        values.put(Contract.Pins.COLUMN_KEY, enteredValues.getEnteredKey());
        values.put(Contract.Pins.COLUMN_PIN, enteredValues.getEnteredPin());
        values.put(Contract.Pins.COLUMN_HINT, enteredValues.getEnteredHint());

        db.insert(Contract.Pins.TABLE_NAME, null, values);
    }
}
