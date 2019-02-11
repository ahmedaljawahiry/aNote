package com.ahmed.anote.pinForm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.db.SQL;
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
    private static TextView keyTextViewMock = mock(TextView.class);
    private static SQL sqlMock = mock(SQL.class);
    private static ToastPrinter toastPrinterMock = mock(ToastPrinter.class);
    private static Context contextMock = mock(Context.class);
    private static View viewMock = mock(View.class);

    @BeforeClass
    public static void setUp() {
        doReturn(buttonMock).when(activityMock).findViewById(eq(R.id.save_pin_button));
        doNothing().when(buttonMock).setOnClickListener(any(SaveButton.class));

        keyTextViewMock = getKeyTextViewMock();

        doReturn(true).when(enteredValuesMock).areValid();

        doReturn(contextMock).when(viewMock).getContext();
        doNothing().when(contextMock).startActivity(any(Intent.class));

        doNothing().when(toastPrinterMock).print(eq(contextMock), anyString(), anyInt());
    }

    @Test
    public void enteredValuesFoundOnClick() {
        EnteredValues enteredValuesMock = mock(EnteredValues.class);
        doReturn(false).when(sqlMock).KEY_EXISTS(anyString());
        new SaveButton(activityMock, enteredValuesMock, sqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(enteredValuesMock, times(1)).find();
    }

    @Test
    public void toastPrintedOnClick() {
        doReturn(false).when(sqlMock).KEY_EXISTS(anyString());
        ToastPrinter toastPrinterMock = Mocks.getToastPrinterMock(contextMock);

        new SaveButton(activityMock, enteredValuesMock, sqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.SAVED), eq(Toast.LENGTH_LONG));
    }

    @Test
    public void newActivityStartedOnClick() {
        doReturn(false).when(sqlMock).KEY_EXISTS(anyString());
        SaveButton saveButton = new SaveButton(activityMock, enteredValuesMock,
                sqlMock, toastPrinterMock);

        saveButton.onClick(viewMock);
        saveButton.editingPin("key");
        saveButton.onClick(viewMock);

        verify(contextMock, times(2)).startActivity(any(Intent.class));
    }

    @Test
    public void pinInsertedOnClick() {
        SQL sqlMock = mock(SQL.class);
        doReturn(false).when(sqlMock).KEY_EXISTS(anyString());
        new SaveButton(activityMock, enteredValuesMock, sqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(sqlMock, times(1)).INSERT_PIN(eq(enteredValuesMock));
    }

    @Test
    public void toastPrintedOnInvalidValues() {
        doReturn(false).when(sqlMock).KEY_EXISTS(anyString());
        ToastPrinter toastPrinterMock = Mocks.getToastPrinterMock(contextMock);

        EnteredValues enteredValuesMock = mock(EnteredValues.class);
        doReturn(false).when(enteredValuesMock).areValid();

        new SaveButton(activityMock, enteredValuesMock, sqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.EMPTY_KEY), eq(Toast.LENGTH_LONG));
    }

    @Test
    public void keyTextColorChangeOnInvalidValues() {
        doReturn(false).when(sqlMock).KEY_EXISTS(anyString());

        EnteredValues enteredValuesMock = mock(EnteredValues.class);
        doReturn(false).when(enteredValuesMock).areValid();

        TextView keyTextViewMock = getKeyTextViewMock();

        new SaveButton(activityMock, enteredValuesMock, sqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(keyTextViewMock, times(1)).setTextColor(eq(Color.parseColor("#8b0000")));
    }

    @Test
    public void toastPrintedOnDuplicateKeyEntry() {
        doReturn(true).when(sqlMock).KEY_EXISTS(anyString());
        ToastPrinter toastPrinterMock = Mocks.getToastPrinterMock(contextMock);

        new SaveButton(activityMock, enteredValuesMock, sqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.DUPLICATE), eq(Toast.LENGTH_LONG));
    }

    @Test
    public void keyTextColorChangeOnDuplicateKeyEnter() {
        doReturn(true).when(sqlMock).KEY_EXISTS(anyString());

        TextView keyTextViewMock = getKeyTextViewMock();

        new SaveButton(activityMock, enteredValuesMock, sqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(keyTextViewMock, times(1)).setTextColor(eq(Color.parseColor("#8b0000")));
    }

    @Test
    public void pinUpdatedOnClick() {
        SQL sqlMock = mock(SQL.class);
        SaveButton saveButton = new SaveButton(activityMock, enteredValuesMock,
                sqlMock, toastPrinterMock);

        saveButton.editingPin("key");
        saveButton.onClick(viewMock);
        verify(sqlMock, times(1)).UPDATE_PIN(eq(enteredValuesMock), eq("key"));
    }

    @Test
    public void duplicateKeyNotificationIfNewKeyEnteredDuringEditAlreadyExists() {
        String existingKey = "existingKey";
        String keyToBeEdited = "keyToBeEdited";

        SQL sqlMock = mock(SQL.class);
        doReturn(true).when(sqlMock).KEY_EXISTS(eq(existingKey));

        EnteredValues enteredValuesMock = mock(EnteredValues.class);
        doReturn(existingKey).when(enteredValuesMock).getEnteredKey();
        doReturn(true).when(enteredValuesMock).areValid();

        ToastPrinter toastPrinterMock = Mocks.getToastPrinterMock(contextMock);

        SaveButton saveButton = new SaveButton(activityMock, enteredValuesMock,
                sqlMock, toastPrinterMock);

        saveButton.editingPin(keyToBeEdited);
        saveButton.onClick(viewMock);

        verify(sqlMock, times(0)).UPDATE_PIN(eq(enteredValuesMock), eq(keyToBeEdited));
        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.DUPLICATE), eq(Toast.LENGTH_LONG));
    }

    private static TextView getKeyTextViewMock() {
        TextView keyTextViewMock = mock(TextView.class);
        doNothing().when(keyTextViewMock).setTextColor(anyInt());
        doReturn(keyTextViewMock).when(activityMock).findViewById(eq(R.id.key_text));

        return keyTextViewMock;
    }
}
