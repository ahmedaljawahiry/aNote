package com.ahmed.anote.db;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class CipherDb {

    private static CipherDb instance;

    private SQLiteDatabase db;
    private Context context;

    public static CipherDb getInstance(Context context) {
        if (instance == null) {
            instance = new CipherDb(context);
        }
        return instance;
    }

    public SQLiteDatabase getDb() {
        SQLiteDatabase.loadLibs(context);
        return db;
    }

    private CipherDb(Context context) {
        this.context = context;
        SQLiteDatabase.loadLibs(context);
        File dbFile = context.getDatabasePath(DbHelper.DATABASE_NAME);
        this.db = SQLiteDatabase.openOrCreateDatabase(dbFile, "ahmed", null);
    }
}
