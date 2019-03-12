package com.ahmed.anote.db.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.ahmed.anote.db.Contract;
import com.ahmed.anote.forms.note.EnteredValues;

public class NoteSQL {

    private SQLiteDatabase db;

    public NoteSQL(@NonNull SQLiteDatabase db) {
        if (db.isReadOnly()) {
            throw new SQLException("The input DB is read only!");
        }
        this.db = db;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + Contract.Notes.TABLE_NAME + " (" +
             Contract.Notes._ID + " INTEGER PRIMARY KEY," +
             Contract.Notes.COLUMN_TITLE + " TEXT UNIQUE," +
             Contract.Notes.COLUMN_TEXT + " TEXT)";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + Contract.Notes.TABLE_NAME;

    public void INSERT(EnteredValues enteredValues) {
        ContentValues values = new ContentValues();
        values.put(Contract.Notes.COLUMN_TITLE, enteredValues.getEnteredTitle());
        values.put(Contract.Notes.COLUMN_TEXT, enteredValues.getEnteredNote());

        db.insert(Contract.Notes.TABLE_NAME, null, values);
    }

    public void UPDATE(EnteredValues enteredValues, String title) {
        ContentValues values = new ContentValues();
        values.put(Contract.Notes.COLUMN_TITLE, enteredValues.getEnteredTitle());
        values.put(Contract.Notes.COLUMN_TEXT, enteredValues.getEnteredNote());

        db.update(Contract.Notes.TABLE_NAME,
                values,
                Contract.Notes.COLUMN_TITLE + "=?",
                new String[] {title});
    }

    public boolean TITLE_EXISTS(String title) {
        Cursor cursor = db.query(
                Contract.Notes.TABLE_NAME,
                null,
                Contract.Notes.COLUMN_TITLE + "=?",
                new String[] {title},
                null, null, null);

        return (cursor.getCount() >= 1) ? true : false;
    }

    public Cursor GET_TABLE() {
        return db.query(Contract.Notes.TABLE_NAME, null, null, null, null, null, null);
    }

    public String GET_NOTE_FROM_TITLE(String title) {
        Cursor cursor = db.query(
                Contract.Notes.TABLE_NAME,
                new String[] {Contract.Notes.COLUMN_TEXT},
                Contract.Notes.COLUMN_TITLE + "=?",
                new String[] {title},
                null, null, null);

        int rowCount = cursor.getCount();
        if (rowCount > 1) {
            throw new SQLException(rowCount + " rows returned. Expected just 1.");
        }

        if (cursor.moveToNext()) {
            String pin = cursor.getString(cursor.getColumnIndex(Contract.Notes.COLUMN_TEXT));
            cursor.close();
            return pin;
        }
        else {
            cursor.close();
            throw new SQLException("Note corresponding to given title not found.");
        }
    }

    public void DELETE(String title) {
        db.delete(
                Contract.Notes.TABLE_NAME,
                Contract.Notes.COLUMN_TITLE + "=?",
                new String[] {title});
    }
}
