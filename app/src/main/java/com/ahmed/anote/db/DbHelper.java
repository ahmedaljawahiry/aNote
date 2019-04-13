package com.ahmed.anote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ahmed.anote.db.sql.NoteSQL;
import com.ahmed.anote.db.sql.PinSQL;

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper instance = null;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "aNote.db";

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    private DbHelper(Context context) {
        // private to ensure single instance is used.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new PinSQL(db).CREATE_TABLE();
        new NoteSQL(db).CREATE_TABLE();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
