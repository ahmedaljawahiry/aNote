package com.ahmed.anote.db.sql;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

import com.ahmed.anote.db.Contract;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.forms.FormInputValues;

import java.io.File;

public class PinSQL implements SqlQueries {

    private SQLiteDatabase db;

    public PinSQL(Activity activity) {
        SQLiteDatabase.loadLibs(activity);
        File dbFile = activity.getDatabasePath(DbHelper.DATABASE_NAME);
        this.db = SQLiteDatabase.openOrCreateDatabase(dbFile, "ahmed", null);
        this.CREATE_TABLE();
    }

    public void CREATE_TABLE() {
        String sql = "CREATE TABLE IF NOT EXISTS " + Contract.Pins.TABLE_NAME + " (" +
                Contract.Pins._ID + " INTEGER PRIMARY KEY," +
                Contract.Pins.COLUMN_PIN + " INTEGER," +
                Contract.Pins.COLUMN_KEY + " TEXT UNIQUE, " +
                Contract.Pins.COLUMN_HINT + " TEXT," +
                Contract.Pins.COLUMN_SECURITY_LEVEL + " INTEGER DEFAULT 0)";
        db.execSQL(sql);
    }

    public void DELETE_TABLE() {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Pins.TABLE_NAME);
    }

    public void INSERT(FormInputValues userInput) {
        db.insert(
                Contract.Pins.TABLE_NAME,
                null,
                convertDbMapToContent(userInput));
    }

    public void UPDATE(FormInputValues userInput, String key) {
        db.update(
                Contract.Pins.TABLE_NAME,
                convertDbMapToContent(userInput),
                Contract.Pins.COLUMN_KEY + "=?",
                new String[] {key});
    }

    public boolean RECORD_EXISTS(String key) {
        try (Cursor cursor = db.query(
                Contract.Pins.TABLE_NAME,
                null,
                Contract.Pins.COLUMN_KEY + "=?",
                new String[] {key},
                null, null, null)
        ) {

            return (cursor.getCount() >= 1);
        }
    }

    public Cursor GET_TABLE() {
        return db.query(Contract.Pins.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor GET_NOTE_FROM_PK(String key) {
        Cursor cursor = db.query(
                Contract.Pins.TABLE_NAME,
                new String[]{Contract.Pins.COLUMN_PIN, Contract.Pins.COLUMN_SECURITY_LEVEL},
                Contract.Pins.COLUMN_KEY + "=?",
                new String[]{key},
                null, null, null);

        int rowCount = cursor.getCount();
        if (rowCount > 1) {
            cursor.close();
            throw new SQLException(rowCount + " rows returned. Expected just 1.");
        }

        if (cursor.moveToNext()) {
            return cursor;
        } else {
            cursor.close();
            throw new SQLException("Pin corresponding to given key not found.");
        }
    }

    public void DELETE(String pk) {
        db.delete(
                Contract.Pins.TABLE_NAME,
                Contract.Pins.COLUMN_KEY + "=?",
                new String[] {pk});
    }
}
