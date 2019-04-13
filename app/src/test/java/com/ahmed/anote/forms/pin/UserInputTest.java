package com.ahmed.anote.forms.pin;

import android.app.Activity;
import android.widget.EditText;

import com.ahmed.anote.R;
import com.ahmed.anote.forms.Mocks;

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
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_key));
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_pin));
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_hint));

        assertAllEmpty(new UserInput(activityMock));
    }

    @Test
    public void valuesSetToEmptyStringsIfViewNotFound() {
        Activity activityMock = mock(Activity.class);

        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_key));
        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_pin));
        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_hint));

        assertAllEmpty(new UserInput(activityMock));
    }

    private void assertAllEmpty(UserInput userInput) {
        assertThat(userInput.getEnteredKey()).isEmpty();
        assertThat(userInput.getEnteredPin()).isEmpty();
        assertThat(userInput.getEnteredHint()).isEmpty();
    }

    @Test
    public void valuesSetIfValuesWereEntered() {
        UserInput userInput = new UserInput(Mocks.getActivityMockWithPinValues());
        assertThat(userInput.getEnteredKey()).isEqualTo(Mocks.KEY_VALUE);
        assertThat(userInput.getEnteredPin()).isEqualTo(Mocks.PIN_VALUE);
        assertThat(userInput.getEnteredHint()).isEqualTo(Mocks.HINT_VALUE);
    }
}
