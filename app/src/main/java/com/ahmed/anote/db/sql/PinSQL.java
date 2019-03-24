package com.ahmed.anote.db.sql;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.ahmed.anote.db.Contract;
import com.ahmed.anote.forms.FormInputValues;

public class PinSQL implements SqlQueries {

    private SQLiteDatabase db;

    public PinSQL(@NonNull SQLiteDatabase db) {
        if (db.isReadOnly()) {
            throw new SQLException("The input DB is read only!");
        }
        this.db = db;
    }

    public void CREATE_TABLE() {
        String sql = "CREATE TABLE " + Contract.Pins.TABLE_NAME + " (" +
                Contract.Pins._ID + " INTEGER PRIMARY KEY," +
                Contract.Pins.COLUMN_PIN + " INTEGER," +
                Contract.Pins.COLUMN_KEY + "TEXT UNIQUE, " + // no space here...?
                Contract.Pins.COLUMN_HINT + " TEXT)";
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
        Cursor cursor = db.query(
                Contract.Pins.TABLE_NAME,
                null,
                Contract.Pins.COLUMN_KEY + "=?",
                new String[] {key},
                null, null, null);

        return (cursor.getCount() >= 1) ? true : false;
    }

    public Cursor GET_TABLE() {
        return db.query(Contract.Pins.TABLE_NAME, null, null, null, null, null, null);
    }

    public String GET_NOTE_FROM_PK(String key) {
        Cursor cursor = db.query(
                Contract.Pins.TABLE_NAME,
                new String[] {Contract.Pins.COLUMN_PIN},
                Contract.Pins.COLUMN_KEY + "=?",
                new String[] {key},
                null, null, null);

        int rowCount = cursor.getCount();
        if (rowCount > 1) {
            throw new SQLException(rowCount + " rows returned. Expected just 1.");
        }

        if (cursor.moveToNext()) {
            String pin = cursor.getString(cursor.getColumnIndex(Contract.Pins.COLUMN_PIN));
            cursor.close();
            return pin;
        }
        else {
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
