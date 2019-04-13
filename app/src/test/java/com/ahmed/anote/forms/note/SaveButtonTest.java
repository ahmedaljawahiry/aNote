package com.ahmed.anote.forms.note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.db.sql.NoteSQL;
import com.ahmed.anote.forms.Mocks;
import com.ahmed.anote.common.util.ToastPrinter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SaveButtonTest {

    private static Activity activityMock = Mocks.getActivityMockWithNoteValues();
    private static UserInput userInputMock = mock(UserInput.class);
    private static Button buttonMock = mock(Button.class);
    private static NoteSQL noteSqlMock = mock(NoteSQL.class);
    private static ToastPrinter toastPrinterMock = mock(ToastPrinter.class);
    private static Context contextMock = mock(Context.class);
    private static View viewMock = mock(View.class);

    @BeforeAll
    public static void setUp() {
        doReturn(buttonMock).when(activityMock).findViewById(eq(R.id.save_note_button));

        doReturn(true).when(userInputMock).areValid();

        contextMock = getContextMock(viewMock);
    }

    @Test
    public void enteredValuesFoundOnClick() {
        UserInput userInputMock = mock(UserInput.class);
        doReturn(false).when(noteSqlMock).RECORD_EXISTS(anyString());
        new SaveButton(activityMock, userInputMock, noteSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(userInputMock, times(1)).find();
    }

    @Test
    public void toastPrintedOnClick() {
        doReturn(false).when(noteSqlMock).RECORD_EXISTS(anyString());
        ToastPrinter toastPrinterMock = mock(ToastPrinter.class);

        new SaveButton(activityMock, userInputMock, noteSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.SAVED), eq(Toast.LENGTH_SHORT));
    }

    @Test
    public void newActivityStartedOnClick() {
        View viewMock = mock(View.class);
        Context contextMock = getContextMock(viewMock);

        doReturn(false).when(noteSqlMock).RECORD_EXISTS(anyString());
        SaveButton saveButton = new SaveButton(activityMock, userInputMock,
                noteSqlMock, toastPrinterMock);

        saveButton.onClick(viewMock);
        verify(contextMock, times(1)).startActivity(any(Intent.class));

        saveButton.editOnly("title");
        saveButton.onClick(viewMock);
        verify(contextMock, times(2)).startActivity(any(Intent.class));
    }

    @Test
    public void noteInsertedOnClick() {
        NoteSQL noteSqlMock = mock(NoteSQL.class);
        doReturn(false).when(noteSqlMock).RECORD_EXISTS(anyString());
        new SaveButton(activityMock, userInputMock, noteSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(noteSqlMock, times(1)).INSERT(eq(userInputMock));
    }

    @Test
    public void toastPrintedOnInvalidValues() {
        doReturn(false).when(noteSqlMock).RECORD_EXISTS(anyString());
        ToastPrinter toastPrinterMock = mock(ToastPrinter.class);

        UserInput userInputMock = mock(UserInput.class);
        doReturn(false).when(userInputMock).areValid();

        new SaveButton(activityMock, userInputMock, noteSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.EMPTY_TITLE), eq(Toast.LENGTH_SHORT));
    }

    @Test
    public void toastPrintedOnDuplicateKeyEntry() {
        doReturn(true).when(noteSqlMock).RECORD_EXISTS(anyString());
        ToastPrinter toastPrinterMock = mock(ToastPrinter.class);

        new SaveButton(activityMock, userInputMock, noteSqlMock, toastPrinterMock)
                .onClick(viewMock);

        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.DUPLICATE), eq(Toast.LENGTH_SHORT));
    }

    @Test
    public void noteUpdatedOnClick() {
        NoteSQL noteSqlMock = mock(NoteSQL.class);
        SaveButton saveButton = new SaveButton(activityMock, userInputMock,
                noteSqlMock, toastPrinterMock);

        saveButton.editOnly("title");
        saveButton.onClick(viewMock);
        verify(noteSqlMock, times(1)).UPDATE(eq(userInputMock), eq("title"));
    }

    @Test
    public void duplicateKeyNotificationIfNewKeyEnteredDuringEditAlreadyExists() {
        String existingTitle = "existingTitle";
        String titeOfNoteToBeEdited = "titeOfNoteToBeEdited";

        NoteSQL noteSqlMock = mock(NoteSQL.class);
        doReturn(true).when(noteSqlMock).RECORD_EXISTS(eq(existingTitle));

        UserInput userInputMock = mock(UserInput.class);
        doReturn(existingTitle).when(userInputMock).getEnteredTitle();
        doReturn(true).when(userInputMock).areValid();

        ToastPrinter toastPrinterMock = mock(ToastPrinter.class);

        SaveButton saveButton = new SaveButton(activityMock, userInputMock,
                noteSqlMock, toastPrinterMock);

        saveButton.editOnly(titeOfNoteToBeEdited);
        saveButton.onClick(viewMock);

        verify(noteSqlMock, times(0)).UPDATE(eq(userInputMock), eq(titeOfNoteToBeEdited));
        verify(toastPrinterMock, times(1))
                .print(eq(contextMock), eq(SaveButton.DUPLICATE), eq(Toast.LENGTH_SHORT));
    }

    private static Context getContextMock(View viewMock) {
        Context contextMock = mock(Context.class);
        doReturn(contextMock).when(viewMock).getContext();
        return contextMock;
    }

}
