package com.anote.db.encryption;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;

import static com.anote.db.CipherDb.DB_PASSWORD_IV_KEY;
import static com.anote.db.CipherDb.PREF_FILE_NAME;
import static com.anote.db.encryption.AndroidKeyStore.AES_MODE;

public class Decrypter {

    private AndroidKeyStore keyStore;
    private Context context;

    public Decrypter(AndroidKeyStore keyStore, Context context) {
        this.keyStore = keyStore;
        this.context = context;
    }

    public String decrypt(String encryptedString) throws DecrypterException, KeyStoreException {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedString);
            Key key = keyStore.getKey(AndroidKeyStore.ALIAS);
            Cipher cipher = Cipher.getInstance(AES_MODE);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, retrieveIv());

            cipher.init(cipher.DECRYPT_MODE, key, gcmSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            throw new DecrypterException(
                    "Error occurred while retrieving Cipher: " + e.getMessage()
            );
        }
        catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
            throw new DecrypterException(
                    "Error occurred while initialising Cipher: " + e.getMessage()
            );
        }
        catch (BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new DecrypterException(
                    "Error occurred while performing final decryption: " + e.getMessage()
            );
        }
    }

    private byte[] retrieveIv() {
        SharedPreferences sharedPref = context.getSharedPreferences(
                PREF_FILE_NAME, Context.MODE_PRIVATE
        );
        String ivString = sharedPref.getString(DB_PASSWORD_IV_KEY, null);
        return Base64.getDecoder().decode(ivString);
    }
}
