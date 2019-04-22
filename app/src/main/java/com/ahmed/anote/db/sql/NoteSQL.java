package com.ahmed.anote.db.sql;

import net.sqlcipher.Cursor;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

import com.ahmed.anote.db.CipherDb;
import com.ahmed.anote.db.Contract;
import com.ahmed.anote.forms.FormInputValues;


public class NoteSQL implements SqlQueries {

    private SQLiteDatabase db;

    public NoteSQL(CipherDb cipherDb) {
        this.db = cipherDb.getDb();
        this.CREATE_TABLE();
    }

    public void CREATE_TABLE() {
        String sql = "CREATE TABLE IF NOT EXISTS " + Contract.Notes.TABLE_NAME + " (" +
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

            return (cursor.getCount() >= 1);
        }
    }

    public Cursor GET_TABLE() {
        return db.query(Contract.Notes.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor GET_NOTE_FROM_PK(String title) {
        Cursor cursor = db.query(
                Contract.Notes.TABLE_NAME,
                new String[] {Contract.Notes.COLUMN_TEXT, Contract.Notes.COLUMN_SECURITY_LEVEL},
                Contract.Notes.COLUMN_TITLE + "=?",
                new String[] {title},
                null, null, null);

        int rowCount = cursor.getCount();
        if (rowCount > 1) {
            cursor.close();
            throw new SQLException(rowCount + " rows returned. Expected just 1.");
        }

        if (cursor.moveToNext()) {
            return cursor;
        }
        else {
            cursor.close();
            throw new SQLException("Note corresponding to given title not found.");
        }
    }

    public void DELETE(String pk) {
        db.delete(
                Contract.Notes.TABLE_NAME,
                Contract.Notes.COLUMN_TITLE + "=?",
                new String[] {pk});
    }
}
