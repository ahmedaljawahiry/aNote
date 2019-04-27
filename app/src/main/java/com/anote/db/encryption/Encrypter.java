package com.anote.db.encryption;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;

public class Encrypter {

    private byte[] ivBytes;
    private byte[] encryptedBytes;
    private AndroidKeyStore keyStore;

    public Encrypter(AndroidKeyStore keyStore) {
        this.keyStore = keyStore;
        generateIvBytes();
    }

    public String encrypt(String textToEncrypt) throws EncrypterException {
        try {
            Cipher cipher = Cipher.getInstance(AndroidKeyStore.AES_MODE);
            Key key = keyStore.generateKey(AndroidKeyStore.ALIAS);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, ivBytes);

            cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);
            encryptedBytes = cipher.doFinal(textToEncrypt.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            throw new EncrypterException(
                    "Error occurred while retrieving Cipher: " + e.getMessage()
            );
        }
        catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
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

    public String getIvString() {
        return Base64.getEncoder().encodeToString(ivBytes);
    }

    private void generateIvBytes() {
        SecureRandom secureRandom = new SecureRandom();
        ivBytes = new byte[12];
        secureRandom.nextBytes(ivBytes);
    }
}
