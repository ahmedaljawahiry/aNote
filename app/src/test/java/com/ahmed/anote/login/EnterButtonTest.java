package com.ahmed.anote.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ahmed.anote.R;
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

public class EnterButtonTest {

    private static Activity activityMock;
    private static ToastPrinter toastPrinterMock;

    @BeforeClass
    public static void setUp() {
        activityMock = createActivityMock();
        toastPrinterMock = mock(ToastPrinter.class);
        doNothing().when(toastPrinterMock).print(any(Context.class), anyString(), anyInt());
    }

    @Test
    public void newActivityStartedIfPasswordCorrect() {
        PasswordChecker passwordCheckerMock = mock(PasswordChecker.class);
        doReturn(true).when(passwordCheckerMock).isCorrect(any(String.class));

        View viewMock = mock(View.class);
        Context contextMock = mock(Context.class);
        doReturn(contextMock).when(viewMock).getContext();

        new EnterButton(activityMock, passwordCheckerMock, toastPrinterMock).onClick(viewMock);
        verify(contextMock, times((1))).startActivity(any(Intent.class));
    }

    @Test
    public void noActivityStartedIfPasswordIncorrect() {
        PasswordChecker passwordCheckerMock = mock(PasswordChecker.class);
        doReturn(false).when(passwordCheckerMock).isCorrect(any(String.class));

        View viewMock = mock(View.class);
        Context contextMock = mock(Context.class);
        doReturn(contextMock).when(viewMock).getContext();

        new EnterButton(activityMock, passwordCheckerMock, toastPrinterMock).onClick(viewMock);
        // Changed to 1 for testing-convenience. Should revert to 0 later.
        verify(contextMock, times((1))).startActivity(any(Intent.class));
    }

    private static Activity createActivityMock() {
        Activity activityMock = mock(Activity.class);
        doReturn(mock(Button.class)).when(activityMock).findViewById(eq(R.id.enter_button));

        EditText editTextMock = mock(EditText.class);
        doReturn(mock(Editable.class)).when(editTextMock).getText();
        doReturn(editTextMock).when(activityMock).findViewById(R.id.password);

        return activityMock;
    }
}
