package com.ahmed.anote.pinForm;

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
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_key));
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_pin));
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_hint));

        assertAllEmpty(new EnteredValues(activityMock));
    }

    @Test
    public void valuesSetToEmptyStringsIfViewNotFound() {
        Activity activityMock = mock(Activity.class);

        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_key));
        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_pin));
        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_hint));

        assertAllEmpty(new EnteredValues(activityMock));
    }

    private void assertAllEmpty(EnteredValues enteredValues) {
        assertThat(enteredValues.getEnteredKey()).isEmpty();
        assertThat(enteredValues.getEnteredPin()).isEmpty();
        assertThat(enteredValues.getEnteredHint()).isEmpty();
    }

    @Test
    public void valuesSetIfValuesWereEntered() {
        EnteredValues enteredValues = new EnteredValues(Mocks.getActivityMockWithPinValues());
        assertThat(enteredValues.getEnteredKey()).isEqualTo(Mocks.KEY_VALUE);
        assertThat(enteredValues.getEnteredPin()).isEqualTo(Mocks.PIN_VALUE);
        assertThat(enteredValues.getEnteredHint()).isEqualTo(Mocks.HINT_VALUE);
    }
}
