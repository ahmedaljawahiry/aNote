package com.anote.db.encryption;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class EncrypterTest {

    @Test
    public void canEncrypt() throws EncrypterException, NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        SecretKey secretKey = keyGen.generateKey();

        AndroidKeyStore keyStoreMock = mock(AndroidKeyStore.class);
        doReturn(secretKey).when(keyStoreMock).generateKey(eq(AndroidKeyStore.ALIAS));

        Encrypter encrypter = new Encrypter(keyStoreMock);
        String decryptedText = "hello";
        String encryption = encrypter.encrypt(decryptedText);

        assertThat(encryption).isNotEqualTo(decryptedText);
        assertThat(encryption).isNotNull();
    }
}
