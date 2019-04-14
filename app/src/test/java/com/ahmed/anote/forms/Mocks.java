package com.ahmed.anote.forms;

import android.app.Activity;
import android.text.Editable;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ahmed.anote.R;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Mocks {

    public static final String KEY_VALUE = "KEY";
    public static final String PIN_VALUE = "PIN";
    public static final String HINT_VALUE = "HINT";
    public static final String TITLE_VALUE = "TITLE";
    public static final String NOTE_VALUE = "NOTE";
    public static final boolean SECURE_VALUE = true;

    public static Activity getActivityMockWithPinValues() {
        Activity activityMock = mock(Activity.class);

        setTextReturnValue(activityMock, R.id.entered_key, KEY_VALUE);
        setTextReturnValue(activityMock, R.id.entered_hint, HINT_VALUE);
        setTextReturnValue(activityMock, R.id.entered_pin, PIN_VALUE);
        setCheckboxReturnValue(activityMock, R.id.pin_locked_checkbox, SECURE_VALUE);

        return activityMock;
    }

    public static Activity getActivityMockWithNoteValues() {
        Activity activityMock = mock(Activity.class);

        setTextReturnValue(activityMock, R.id.entered_title, TITLE_VALUE);
        setTextReturnValue(activityMock, R.id.entered_note, NOTE_VALUE);

        return activityMock;
    }

    private static void setTextReturnValue(Activity activityMock, int id, String returnValue) {
        EditText editTextMock = mock(EditText.class);
        Editable editableMock = mock(Editable.class);

        doReturn(editTextMock).when(activityMock).findViewById(eq(id));
        doReturn(editableMock).when(editTextMock).getText();
        doReturn(returnValue).when(editableMock).toString();
    }

    private static void setCheckboxReturnValue(Activity activityMock, int id, boolean returnValue) {
        CheckBox checkBoxMock = mock(CheckBox.class);
        doReturn(returnValue).when(checkBoxMock).isChecked();

        doReturn(checkBoxMock).when(activityMock).findViewById(id);
    }
}
