package com.ahmed.anote.db.sql;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.ahmed.anote.db.Contract;
import com.ahmed.anote.forms.FormInputValues;

public class NoteSQL implements SqlQueries {

    private SQLiteDatabase db;

    public NoteSQL(@NonNull SQLiteDatabase db) {
        if (db.isReadOnly()) {
            throw new SQLException(READ_ONLY_DB_ERROR_MSG);
        }
        this.db = db;
    }

    public void CREATE_TABLE() {
        String sql = "CREATE TABLE " + Contract.Notes.TABLE_NAME + " (" +
                Contract.Notes._ID + " INTEGER PRIMARY KEY," +
                Contract.Notes.COLUMN_TITLE + " TEXT UNIQUE," +
                Contract.Notes.COLUMN_TEXT + " TEXT," +
                Contract.Notes.COLUMN_SECURITY_LEVEL + " INTEGER DEFAULT 0)";
        db.execSQL(sql);
    }

    public void DELETE_TABLE() {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Notes.TABLE_NAME);
    }

    public void INSERT(FormInputValues userInput) {
        db.insert(
                Contract.Notes.TABLE_NAME,
                null,
                convertDbMapToContent(userInput));
    }

    public void UPDATE(FormInputValues userInput, String title) {
        db.update(
                Contract.Notes.TABLE_NAME,
                convertDbMapToContent(userInput),
                Contract.Notes.COLUMN_TITLE + "=?",
                new String[] {title});
    }

    public boolean RECORD_EXISTS(String title) {
        try (Cursor cursor = db.query(
                Contract.Notes.TABLE_NAME,
                null,
                Contract.Notes.COLUMN_TITLE + "=?",
                new String[] {title},
                null, null, null)
        ) {

            return (cursor.getCount() >= 1) ? true : false;
        }
    }

    public Cursor GET_TABLE() {
        return db.query(Contract.Notes.TABLE_NAME, null, null, null, null, null, null);
    }

    public String GET_NOTE_FROM_PK(String title) {
        try (Cursor cursor = db.query(
                Contract.Notes.TABLE_NAME,
                new String[] {Contract.Notes.COLUMN_TEXT},
                Contract.Notes.COLUMN_TITLE + "=?",
                new String[] {title},
                null, null, null)
        ) {

            int rowCount = cursor.getCount();
            if (rowCount > 1) {
                throw new SQLException(rowCount + " rows returned. Expected just 1.");
            }

            if (cursor.moveToNext()) {
                String note = cursor.getString(cursor.getColumnIndex(Contract.Notes.COLUMN_TEXT));
                return note;
            }
            else {
                throw new SQLException("Note corresponding to given title not found.");
            }
        }


    }

    public void DELETE(String pk) {
        db.delete(
                Contract.Notes.TABLE_NAME,
                Contract.Notes.COLUMN_TITLE + "=?",
                new String[] {pk});
    }
}
