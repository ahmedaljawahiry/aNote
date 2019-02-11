package com.ahmed.anote.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.widget.EditText;

import com.ahmed.anote.R;
import com.ahmed.anote.db.DbHelper;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Mocks {

    public static final String KEY_VALUE = "KEY";
    public static final String PIN_VALUE = "KEY";
    public static final String HINT_VALUE = "HINT";

    public static Activity getActivityMockWithPinValues() {
        Activity activityMock = mock(Activity.class);

        setReturnValue(activityMock, R.id.entered_key, KEY_VALUE);
        setReturnValue(activityMock, R.id.entered_hint, HINT_VALUE);
        setReturnValue(activityMock, R.id.entered_pin, PIN_VALUE);

        return activityMock;
    }

    private static void setReturnValue(Activity activityMock, int id, String returnValue) {
        EditText editTextMock = mock(EditText.class);
        Editable editableMock = mock(Editable.class);

        doReturn(editTextMock).when(activityMock).findViewById(eq(id));
        doReturn(editableMock).when(editTextMock).getText();
        doReturn(returnValue).when(editableMock).toString();
    }

    public static DbHelper getDbHelperMockWithMockedDb() {
        DbHelper dbHelperMock = mock(DbHelper.class);
        SQLiteDatabase dbMock = mock(SQLiteDatabase.class);
        doReturn(1234L).when(dbMock).insert(anyString(), anyString(), any(ContentValues.class));
        doReturn(dbMock).when(dbHelperMock).getWritableDatabase();

        return dbHelperMock;
    }

    public static ToastPrinter getToastPrinterMock(Context contextMock) {
        ToastPrinter toastPrinterMock = mock(ToastPrinter.class);
        doNothing().when(toastPrinterMock).print(eq(contextMock), anyString(), anyInt());

        return toastPrinterMock;
    }
}
