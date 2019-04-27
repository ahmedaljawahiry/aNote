package com.anote.db.encryption;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
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
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import static com.anote.db.CipherDb.KEY_ALIAS;
import static com.anote.db.encryption.Util.AES_MODE;
import static com.anote.db.encryption.Util.ANDROID_KEY_STORE;

public class Encrypter {

    private KeyStore keyStore;
    private byte[] IV;
    private byte[] encryption;

    public Encrypter() throws KeyStoreException {
        keyStore = Util.initKeyStore();
    }

    public void encrypt(String textToEncrypt) throws EncrypterException {

        try {
            Cipher cipher = Cipher.getInstance(AES_MODE);
            IV = cipher.getIV();
            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    generateKey(),
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

    private SecretKey generateKey() throws EncrypterException {
        SecretKey key = null;
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

            throw new EncrypterException(
                    "Error occurred while generating encryption key: " + e.getMessage()
            );
        }
        return key;
    }

}
