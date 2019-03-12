package com.ahmed.anote.forms.note;

import android.app.Activity;
import android.widget.EditText;

import com.ahmed.anote.R;
import com.ahmed.anote.util.Mocks;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class EnteredValuesTest {

    @Test
    public void valuesSetToEmptyStringIfNothingEntered() {
        Activity activityMock = mock(Activity.class);
        EditText editTextMock = mock(EditText.class);

        doReturn(null).when(editTextMock).getText();
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_title));
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_note));

        assertAllEmpty(new EnteredValues(activityMock));
    }

    @Test
    public void valuesSetToEmptyStringsIfViewNotFound() {
        Activity activityMock = mock(Activity.class);

        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_title));
        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_note));

        assertAllEmpty(new EnteredValues(activityMock));
    }

    private void assertAllEmpty(EnteredValues enteredValues) {
        assertThat(enteredValues.getEnteredTitle()).isEmpty();
        assertThat(enteredValues.getEnteredNote()).isEmpty();
    }

    @Test
    public void valuesSetIfValuesWereEntered() {
        EnteredValues enteredValues = new EnteredValues(Mocks.getActivityMockWithNoteValues());
        assertThat(enteredValues.getEnteredTitle()).isEqualTo(Mocks.TITLE_VALUE);
        assertThat(enteredValues.getEnteredNote()).isEqualTo(Mocks.NOTE_VALUE);
    }
}
