package com.anote.db.encryption;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AndroidKeyStore {

    public static final String NAME = "AndroidKeyStore";
    public static final String AES_MODE = "AES/GCM/NoPadding";
    public static final String ALIAS = "aNote";

    private KeyStore store;

    public AndroidKeyStore() throws KeyStoreException {
        try {
            KeyStore keyStore = KeyStore.getInstance(NAME);
            keyStore.load(null);
            this.store = keyStore;
        }
        catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            throw new KeyStoreException(
                    "Error occurred while initialising KeyStore: " + e.getMessage()
            );
        }
    }

    public Key generateKey(String alias) throws EncrypterException {
        try {
            KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                    alias,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT
            )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setRandomizedEncryptionRequired(false)
                    .build();

            KeyGenerator keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, AndroidKeyStore.NAME
            );
            keyGenerator.init(spec);
            return keyGenerator.generateKey();
        }
        catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new EncrypterException(
                    "Error occurred while generating encryption key: " + e.getMessage()
            );
        }
    }

    public Key getKey(String alias) throws KeyStoreException {
        try {
            return store.getKey(alias, null);
        }
        catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            e.printStackTrace();
            throw new KeyStoreException(
                    "Error occurred while retrieving key from store: " + e.getMessage()
            );
        }
    }
}
