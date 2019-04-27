package com.anote.db.encryption;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;

public class Encrypter {

    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final String AES_MODE = "AES/GCM/NoPadding";
    private static final String KEY_ALIAS = "aNote";

    private KeyStore keyStore;
    private Key key;
    private byte[] IV;
    private byte[] encryption;

    public Encrypter() throws EncrypterException {
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);
            generateKey();
        }
        catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new EncrypterException(
                    "Error occurred while initialising KeyStore: " + e.getMessage()
            );
        }

        try {
            generateKey();
        }
        catch (KeyStoreException  e) {
            e.printStackTrace();
            throw new EncrypterException(
                    "Error occurred while generating encryption key: " + e.getMessage()
            );
        }
    }

    public void encrypt(String textToEncrypt) throws EncrypterException {

        try {
            Cipher cipher = Cipher.getInstance(AES_MODE);
            IV = cipher.getIV();
            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    key,
                    new GCMParameterSpec(128, IV)
            );
            encryption = cipher.doFinal(textToEncrypt.getBytes());
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            throw new EncrypterException(
                    "Error occurred while retrieving Cipher: " + e.getMessage()
            );
        }
        catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            throw new EncrypterException(
                    "Error occurred while initialising Cipher: " + e.getMessage()
            );
        }
        catch (BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new EncrypterException(
                    "Error occurred while performing final encryption: " + e.getMessage()
            );
        }
    }

    public byte[] getIV() {
        return IV;
    }

    public byte[] getEncryption() {
        return encryption;
    }

    private void generateKey() throws KeyStoreException {
        try {
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE
                );
                keyGenerator.init(
                        new KeyGenParameterSpec.Builder(
                                KEY_ALIAS,
                                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT
                        )
                                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                                .setRandomizedEncryptionRequired(false)
                                .build()
                );
                key = keyGenerator.generateKey();
            }
        } catch (KeyStoreException | NoSuchProviderException
                | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {

            throw new KeyStoreException(
                    "Error occurred while generating encryption key: " + e.getMessage()
            );
        }
    }

}
