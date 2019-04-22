package com.anote.forms.note;

import android.app.Activity;
import android.widget.EditText;

import com.anote.R;
import com.anote.forms.Mocks;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class UserInputTest {

    @Test
    public void valuesSetToEmptyStringIfNothingEntered() {
        Activity activityMock = mock(Activity.class);
        EditText editTextMock = mock(EditText.class);

        doReturn(null).when(editTextMock).getText();
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_title));
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_note));

        UserInput userInput = new UserInput(activityMock);
        assertThat(userInput.getEnteredTitle()).isEmpty();
        assertThat(userInput.getEnteredNote()).isEmpty();
    }

    @Test
    public void valuesSetToEmptyStringsIfViewNotFound() {
        Activity activityMock = mock(Activity.class);

        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_title));
        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_note));

        UserInput userInput = new UserInput(activityMock);
        assertThat(userInput.getEnteredTitle()).isEmpty();
        assertThat(userInput.getEnteredNote()).isEmpty();
    }

    @Test
    public void valuesSetIfValuesWereEntered() {
        UserInput userInput = new UserInput(Mocks.getActivityMockWithNoteValues());
        assertThat(userInput.getEnteredTitle()).isEqualTo(Mocks.TITLE_VALUE);
        assertThat(userInput.getEnteredNote()).isEqualTo(Mocks.NOTE_VALUE);
    }
}
