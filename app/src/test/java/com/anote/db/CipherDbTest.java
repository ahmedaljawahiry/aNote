package com.anote.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.anote.db.encryption.DecrypterException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CipherDbTest {


    @Test
    public void DecrypterExceptionIfErrorOccursWithKeyStore() {
        Context contextMock = mock(Context.class);
        SharedPreferences sharedPreferencesMock = mock(SharedPreferences.class);

        doReturn(sharedPreferencesMock).when(contextMock).getSharedPreferences(
                eq(CipherDb.PREF_FILE_NAME),
                eq(Context.MODE_PRIVATE)
        );

        doReturn("encryptedPassword").when(sharedPreferencesMock).getString(
                eq(CipherDb.DB_PASSWORD_KEY),
                any()
        );

        assertThatExceptionOfType(DecrypterException.class)
                .isThrownBy(() -> CipherDb.getInstance(contextMock))
                .withMessageContaining("Error occurred with KeyStore");
    }
}
