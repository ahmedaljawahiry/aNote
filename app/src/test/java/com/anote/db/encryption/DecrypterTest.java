package com.anote.db.encryption;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static com.anote.db.CipherDb.DB_PASSWORD_IV_KEY;
import static com.anote.db.CipherDb.PREF_FILE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


public class DecrypterTest {

    @Test
    public void canDecrypt() throws Exception {
        String decryptedText = "ENCRYPT ME!!";

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        SecretKey secretKey = keyGen.generateKey();

        AndroidKeyStore keyStoreMock = mock(AndroidKeyStore.class);
        doReturn(secretKey).when(keyStoreMock).generateKey(eq(AndroidKeyStore.ALIAS));
        doReturn(secretKey).when(keyStoreMock).getKey(eq(AndroidKeyStore.ALIAS));

        Encrypter encrypter = new Encrypter(keyStoreMock);
        String encryption = encrypter.encrypt(decryptedText);

        assertThat(encryption).isNotEqualTo(decryptedText);
        assertThat(encryption).isNotNull();

        Context contextMock = createContextMock(encrypter.getIvString());

        Decrypter decrypter = new Decrypter(keyStoreMock, contextMock);

        assertThat(decrypter.decrypt(encryption)).isEqualTo(decryptedText);
    }

    private Context createContextMock(String ivString) {
        Context contextMock = mock(Context.class);
        SharedPreferences sharedPrefMock = mock(SharedPreferences.class);

        doReturn(sharedPrefMock).when(contextMock).getSharedPreferences(
                eq(PREF_FILE_NAME), eq(Context.MODE_PRIVATE));

        doReturn(ivString).when(sharedPrefMock).getString(eq(DB_PASSWORD_IV_KEY), any());
        return contextMock;
    }
}
