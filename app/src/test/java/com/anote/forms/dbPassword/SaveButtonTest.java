package com.anote.forms.dbPassword;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anote.R;
import com.anote.common.util.ToastPrinter;
import com.anote.db.CipherDb;
import com.anote.db.encryption.Encrypter;
import com.anote.db.encryption.EncrypterException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SaveButtonTest {

    private static Activity activityMock;
    private View viewMock;
    private Context contextMock;
    private SharedPreferences.Editor editorMock;

    @BeforeAll
    public static void createActivityMock() {
        activityMock = mock(Activity.class);
        Button buttonMock = mock(Button.class);
        doReturn(buttonMock).when(activityMock).findViewById(eq(R.id.save_note_button));
    }

    @BeforeEach
    public void createMocks() {
        viewMock = mock(View.class);
        contextMock = mock(Context.class);
        doReturn(contextMock).when(viewMock).getContext();

        SharedPreferences sharedPreferencesMock = mock(SharedPreferences.class);
        editorMock = mock(SharedPreferences.Editor.class);

        doReturn(sharedPreferencesMock).when(contextMock).getSharedPreferences(
                eq(CipherDb.PREF_FILE_NAME), eq(Context.MODE_PRIVATE)
        );
        doReturn(editorMock).when(sharedPreferencesMock).edit();
    }

    private static Stream<Arguments> inputValueReturnsAndExpectedErrors() {
        return Stream.of(
                arguments(true, false, SaveButton.NOTHING_ENTERED_ERROR),
                arguments(false, false, SaveButton.MISMATCH_ERROR)
        );
    }

    @ParameterizedTest
    @MethodSource("inputValueReturnsAndExpectedErrors")
    public void onClickErrorsHandledCorrectly(
            boolean nothingEntered, boolean areValid, String error) throws EncrypterException {

        InputValues inputValuesMock = mock(InputValues.class);
        doReturn(nothingEntered).when(inputValuesMock).nothingEntered();
        doReturn(areValid).when(inputValuesMock).areValid();

        Encrypter encrypterMock = mock(Encrypter.class);
        ToastPrinter toastPrinterMock = mock(ToastPrinter.class);

        SaveButton saveButton = new SaveButton(
                activityMock, inputValuesMock, encrypterMock, toastPrinterMock
        );

        saveButton.onClick(viewMock);
        verify(toastPrinterMock, times(1)).print(
                eq(contextMock), eq(error), eq(Toast.LENGTH_SHORT)
        );
        verify(contextMock, times(0)).startActivity(any());
        verify(encrypterMock, times(0)).encrypt(anyString());
    }

    @Test
    public void passwordEncryptedOnClick() throws EncrypterException {
        String enteredPassword = "hello123";

        InputValues inputValuesMock = mock(InputValues.class);
        doReturn(false).when(inputValuesMock).nothingEntered();
        doReturn(true).when(inputValuesMock).areValid();
        doReturn(enteredPassword).when(inputValuesMock).getPassword();

        Encrypter encrypterMock = mock(Encrypter.class);
        SaveButton saveButton = new SaveButton(
                activityMock, inputValuesMock, encrypterMock, mock(ToastPrinter.class)
        );

        saveButton.onClick(viewMock);
        verify(encrypterMock, times(1)).encrypt(eq(enteredPassword));
    }

    @Test
    public void correctValuesStoredInSharedPrefsOnClick() throws EncrypterException {
        String enteredPassword = "hello123";
        String encryptedPassword = "jk3k2h35rinind";
        String iv = "3%J£%JCKI£";

        InputValues inputValuesMock = mock(InputValues.class);
        doReturn(false).when(inputValuesMock).nothingEntered();
        doReturn(true).when(inputValuesMock).areValid();
        doReturn(enteredPassword).when(inputValuesMock).getPassword();

        Encrypter encrypterMock = mock(Encrypter.class);
        doReturn(encryptedPassword).when(encrypterMock).encrypt(eq(enteredPassword));
        doReturn(iv).when(encrypterMock).getIvString();

        SaveButton saveButton = new SaveButton(
                activityMock, inputValuesMock, encrypterMock, mock(ToastPrinter.class)
        );

        saveButton.onClick(viewMock);
        verify(editorMock, times(1)).putString(eq(CipherDb.DB_PASSWORD_KEY), eq(encryptedPassword));
        verify(editorMock, times(1)).putString(eq(CipherDb.DB_PASSWORD_IV_KEY), eq(iv));
        verify(editorMock, times(1)).putBoolean(eq(CipherDb.DB_PASSWORD_IS_SET_KEY), eq(true));
        verify(editorMock, times(1)).commit();
    }

    @Test
    public void newActivityStartedOnClick() {
        InputValues inputValuesMock = mock(InputValues.class);
        doReturn(false).when(inputValuesMock).nothingEntered();
        doReturn(true).when(inputValuesMock).areValid();

        SaveButton saveButton = new SaveButton(
                activityMock, inputValuesMock, mock(Encrypter.class), mock(ToastPrinter.class)
        );

        saveButton.onClick(viewMock);
        verify(contextMock, times(1)).startActivity(any());
    }
}
