package com.anote.db.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;

import static com.anote.db.CipherDb.KEY_ALIAS;
import static com.anote.db.encryption.Util.AES_MODE;

public class Decrypter {

    private KeyStore keyStore;

    public Decrypter() throws KeyStoreException {
        keyStore = Util.initKeyStore();
    }

    public String decrypt(byte[] encryptedData, Encrypter encrypter)  throws DecrypterException {
        try {
            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(
                    cipher.DECRYPT_MODE,
                    generateKey(),
                    new GCMParameterSpec(128, encrypter.getIV())
            );
            byte[] decryptedData = cipher.doFinal(encryptedData);
            return new String(decryptedData);
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

    private Key generateKey() throws DecrypterException {
        Key key;
        try {
            key = keyStore.getKey(KEY_ALIAS, null);
        }
        catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            e.printStackTrace();
            throw new DecrypterException(
                    "Error occurred while retrieving key from store: " + e.getMessage()
            );
        }
        return key;
    }
}
