package com.ahmed.anote.pinCreation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.util.Mocks;
import com.ahmed.anote.util.ToastPrinter;

import org.junit.Before;
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

    private Activity activityMock;
    private EnteredValues enteredValuesMock;
    private Button buttonMock;
    private DbHelper dbHelperMock;
    private ToastPrinter toastPrinterMock;
    private Context contextMock;

    @Before
    public void setUp() {
        activityMock = Mocks.getActivityMockWithPinValues();
        buttonMock = mock(Button.class);
        doReturn(buttonMock).when(activityMock).findViewById(eq(R.id.save_pin_button));
        doNothing().when(buttonMock).setOnClickListener(any(SaveButton.class));

        enteredValuesMock = mock(EnteredValues.class);
        dbHelperMock = Mocks.getDbHelperMockWithMockedDb();

        View viewMock = mock(View.class);
        contextMock = mock(Context.class);
        doReturn(contextMock).when(viewMock).getContext();
        doNothing().when(contextMock).startActivity(any(Intent.class));

        toastPrinterMock = mock(ToastPrinter.class);
        doNothing().when(toastPrinterMock).print(eq(contextMock), anyString(), anyInt());

        new SaveButton(activityMock, enteredValuesMock, dbHelperMock, toastPrinterMock)
                .onClick(viewMock);
    }

    @Test
    public void enteredValuesFoundOnClick() {
        verify(enteredValuesMock, times(1)).find();
    }

    @Test
    public void toastPrintedOnClick() {
        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.TOAST_MESSAGE), eq(Toast.LENGTH_LONG));
    }

    @Test
    public void newActivityStartedOnClick() {
        verify(contextMock, times(1)).startActivity(any(Intent.class));
    }
}
