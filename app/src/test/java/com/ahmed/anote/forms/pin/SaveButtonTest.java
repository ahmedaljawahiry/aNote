package com.ahmed.anote.forms.pin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.db.sql.PinSQL;
import com.ahmed.anote.util.Mocks;
import com.ahmed.anote.util.ToastPrinter;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SaveButtonTest {

    private static Activity activityMock = Mocks.getActivityMockWithPinValues();
    private static EnteredValues enteredValuesMock = mock(EnteredValues.class);
    private static Button buttonMock = mock(Button.class);
    private static PinSQL pinSqlMock = mock(PinSQL.class);
    private static ToastPrinter toastPrinterMock = mock(ToastPrinter.class);
    private static Context contextMock = mock(Context.class);
    private static View viewMock = mock(View.class);

    @BeforeClass
    public static void setUp() {
        doReturn(buttonMock).when(activityMock).findViewById(eq(R.id.save_pin_button));

        doReturn(true).when(enteredValuesMock).areValid();

        contextMock = getContextMock(viewMock);
    }

    @Test
    public void enteredValuesFoundOnClick() {
        EnteredValues enteredValuesMock = mock(EnteredValues.class);
        doReturn(false).when(pinSqlMock).KEY_EXISTS(anyString());
        new SaveButton(activityMock, enteredValuesMock, pinSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(enteredValuesMock, times(1)).find();
    }

    @Test
    public void toastPrintedOnClick() {
        doReturn(false).when(pinSqlMock).KEY_EXISTS(anyString());
        ToastPrinter toastPrinterMock = mock(ToastPrinter.class);

        new SaveButton(activityMock, enteredValuesMock, pinSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.SAVED), eq(Toast.LENGTH_SHORT));
    }

    @Test
    public void newActivityStartedOnClick() {
        View viewMock = mock(View.class);
        Context contextMock = getContextMock(viewMock);

        doReturn(false).when(pinSqlMock).KEY_EXISTS(anyString());
        SaveButton saveButton = new SaveButton(activityMock, enteredValuesMock,
                pinSqlMock, toastPrinterMock);

        saveButton.onClick(viewMock);
        verify(contextMock, times(1)).startActivity(any(Intent.class));

        saveButton.editOnly("key");
        saveButton.onClick(viewMock);
        verify(contextMock, times(2)).startActivity(any(Intent.class));
    }

    @Test
    public void pinInsertedOnClick() {
        PinSQL pinSqlMock = mock(PinSQL.class);
        doReturn(false).when(pinSqlMock).KEY_EXISTS(anyString());
        new SaveButton(activityMock, enteredValuesMock, pinSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(pinSqlMock, times(1)).INSERT(eq(enteredValuesMock));
    }

    @Test
    public void toastPrintedOnInvalidValues() {
        doReturn(false).when(pinSqlMock).KEY_EXISTS(anyString());
        ToastPrinter toastPrinterMock = mock(ToastPrinter.class);

        EnteredValues enteredValuesMock = mock(EnteredValues.class);
        doReturn(false).when(enteredValuesMock).areValid();

        new SaveButton(activityMock, enteredValuesMock, pinSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.EMPTY_KEY), eq(Toast.LENGTH_SHORT));
    }

    @Test
    public void keyTextColorChangeOnInvalidValues() {
        doReturn(false).when(pinSqlMock).KEY_EXISTS(anyString());

        EnteredValues enteredValuesMock = mock(EnteredValues.class);
        doReturn(false).when(enteredValuesMock).areValid();

        TextView keyTextViewMock = getKeyTextViewMock();

        new SaveButton(activityMock, enteredValuesMock, pinSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(keyTextViewMock, times(1)).setTextColor(eq(Color.parseColor("#8b0000")));
    }

    @Test
    public void toastPrintedOnDuplicateKeyEntry() {
        doReturn(true).when(pinSqlMock).KEY_EXISTS(anyString());
        ToastPrinter toastPrinterMock = mock(ToastPrinter.class);

        new SaveButton(activityMock, enteredValuesMock, pinSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.DUPLICATE), eq(Toast.LENGTH_SHORT));
    }

    @Test
    public void keyTextColorChangeOnDuplicateKeyEnter() {
        doReturn(true).when(pinSqlMock).KEY_EXISTS(anyString());

        TextView keyTextViewMock = getKeyTextViewMock();

        new SaveButton(activityMock, enteredValuesMock, pinSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(keyTextViewMock, times(1)).setTextColor(eq(Color.parseColor("#8b0000")));
    }

    @Test
    public void pinUpdatedOnClick() {
        PinSQL pinSqlMock = mock(PinSQL.class);
        SaveButton saveButton = new SaveButton(activityMock, enteredValuesMock,
                pinSqlMock, toastPrinterMock);

        saveButton.editOnly("key");
        saveButton.onClick(viewMock);
        verify(pinSqlMock, times(1)).UPDATE(eq(enteredValuesMock), eq("key"));
    }

    @Test
    public void duplicateKeyNotificationIfNewKeyEnteredDuringEditAlreadyExists() {
        String existingKey = "existingKey";
        String keyToBeEdited = "keyToBeEdited";

        PinSQL pinSqlMock = mock(PinSQL.class);
        doReturn(true).when(pinSqlMock).KEY_EXISTS(eq(existingKey));

        EnteredValues enteredValuesMock = mock(EnteredValues.class);
        doReturn(existingKey).when(enteredValuesMock).getEnteredKey();
        doReturn(true).when(enteredValuesMock).areValid();

        ToastPrinter toastPrinterMock = mock(ToastPrinter.class);

        SaveButton saveButton = new SaveButton(activityMock, enteredValuesMock,
                pinSqlMock, toastPrinterMock);

        saveButton.editOnly(keyToBeEdited);
        saveButton.onClick(viewMock);

        verify(pinSqlMock, times(0)).UPDATE(eq(enteredValuesMock), eq(keyToBeEdited));
        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.DUPLICATE), eq(Toast.LENGTH_SHORT));
    }

    private static TextView getKeyTextViewMock() {
        TextView keyTextViewMock = mock(TextView.class);
        doReturn(keyTextViewMock).when(activityMock).findViewById(eq(R.id.key_text));

        return keyTextViewMock;
    }

    private static Context getContextMock(View viewMock) {
        Context contextMock = mock(Context.class);
        doReturn(contextMock).when(viewMock).getContext();
        return contextMock;
    }
}
