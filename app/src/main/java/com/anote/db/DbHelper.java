package com.anote.db;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.File;

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper instance = null;
    private Context context;

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
        this.context = context;
    }

    public SQLiteDatabase openOrCreateDb() {
        File dbFile = context.getDatabasePath(DbHelper.DATABASE_NAME);
        return SQLiteDatabase.openOrCreateDatabase(dbFile, "ahmed", null);
    }

    public void loadLibs() {
        SQLiteDatabase.loadLibs(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
