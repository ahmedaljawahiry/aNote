package com.anote.db.encryption;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public final class Util {

    public static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    public static final String AES_MODE = "AES/GCM/NoPadding";

    private Util() {}

    public static KeyStore initKeyStore() throws KeyStoreException {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);
            return keyStore;
        }
        catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new KeyStoreException(
                    "Error occurred while initialising KeyStore: " + e.getMessage()
            );
        }
    }
}
