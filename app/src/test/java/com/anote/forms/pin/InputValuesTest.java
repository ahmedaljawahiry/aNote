package com.anote.forms.pin;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;

import com.anote.R;
import com.anote.forms.Mocks;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class InputValuesTest {

    @Test
    public void valuesSetToEmptyStringIfNothingEntered() {
        Activity activityMock = mock(Activity.class);
        EditText editTextMock = mock(EditText.class);

        CheckBox checkBoxMock = mock(CheckBox.class);
        doReturn(false).when(checkBoxMock).isChecked();

        doReturn(null).when(editTextMock).getText();
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_key));
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_pin));
        doReturn(editTextMock).when(activityMock).findViewById(eq(R.id.entered_hint));
        doReturn(checkBoxMock).when(activityMock).findViewById(eq(R.id.pin_locked_checkbox));

        assertAllEmpty(new InputValues(activityMock));
    }

    @Test
    public void valuesSetToEmptyStringsIfViewNotFound() {
        Activity activityMock = mock(Activity.class);

        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_key));
        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_pin));
        doReturn(null).when(activityMock).findViewById(eq(R.id.entered_hint));
        doReturn(null).when(activityMock).findViewById(eq(R.id.pin_locked_checkbox));

        assertAllEmpty(new InputValues(activityMock));
    }

    private void assertAllEmpty(InputValues inputValues) {
        assertThat(inputValues.getEnteredKey()).isEmpty();
        assertThat(inputValues.getEnteredPin()).isEmpty();
        assertThat(inputValues.getEnteredHint()).isEmpty();
    }

    @Test
    public void valuesSetIfValuesWereEntered() {
        InputValues inputValues = new InputValues(Mocks.getActivityMockWithPinValues());
        assertThat(inputValues.getEnteredKey()).isEqualTo(Mocks.KEY_VALUE);
        assertThat(inputValues.getEnteredPin()).isEqualTo(Mocks.PIN_VALUE);
        assertThat(inputValues.getEnteredHint()).isEqualTo(Mocks.HINT_VALUE);
        assertThat(inputValues.isLocked()).isEqualTo(Mocks.SECURE_VALUE);
    }
}
