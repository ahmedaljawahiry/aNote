package com.anote.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.anote.db.encryption.Decrypter;
import com.anote.db.encryption.DecrypterException;
import com.anote.db.encryption.AndroidKeyStore;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.security.KeyStoreException;

public class CipherDb {

    public static final String PREF_FILE_NAME = "prefs";
    public static final String DB_PASSWORD_KEY = "dbPassword";
    public static final String DB_PASSWORD_IV_KEY = "dbPasswordIv";
    public static final String DB_PASSWORD_IS_SET_KEY = "dbPasswordIsSet";
    public static final String DECRYPTION_ERROR = "Error occurred while decrypting the DB";
    private static CipherDb instance;

    private SQLiteDatabase db;
    private Context context;

    public static CipherDb getInstance(Context context) throws DecrypterException {
        if (instance == null) {
            instance = new CipherDb(context);
        }
        return instance;
    }

    public SQLiteDatabase getDb() {
        SQLiteDatabase.loadLibs(context);
        return db;
    }

    private CipherDb(Context context) throws DecrypterException {
        this.context = context;
        String password = getDbPassword();
        SQLiteDatabase.loadLibs(context);
        File dbFile = context.getDatabasePath(DbHelper.DATABASE_NAME);
        this.db = SQLiteDatabase.openOrCreateDatabase(dbFile, password, null);
    }

    private String getDbPassword() throws DecrypterException {
        SharedPreferences sharedPref = context.getSharedPreferences(
                PREF_FILE_NAME, Context.MODE_PRIVATE
        );
        String encryptedPassword = sharedPref.getString(DB_PASSWORD_KEY, null);

        try {
            Decrypter decrypter = new Decrypter(
                    new AndroidKeyStore(),
                    context
            );
            String decryptedPassword = decrypter.decrypt(encryptedPassword);
            return decryptedPassword;
        }
        catch (KeyStoreException e) {
            throw new DecrypterException("Error occurred with KeyStore: " + e.getMessage());
        }
    }
}
