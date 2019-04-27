package com.anote.forms.dbPassword;

import android.app.Activity;

import com.anote.R;
import com.anote.forms.Mocks;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class InputValuesTest {

    @Test
    public void invalidInputIfPasswordsDoNotMatch() {
        Activity activityMock = Mocks.getActivityMockWithPasswordValues("p1", "p2");

        InputValues inputValues = new InputValues(activityMock);
        assertThat(inputValues.areValid()).isFalse();
    }

    @Test
    public void validInputIfPasswordsMatch() {
        Activity activityMock = Mocks.getActivityMockWithPasswordValues("p1", "p1");

        InputValues inputValues = new InputValues(activityMock);
        assertThat(inputValues.areValid()).isTrue();
    }

    @Test
    public void invalidInputIfNothingEntered() {
        Activity activityMock = Mocks.getActivityMockWithPasswordValues("", "");

        InputValues inputValues = new InputValues(activityMock);
        assertThat(inputValues.nothingEntered()).isTrue();
        assertThat(inputValues.areValid()).isFalse();
    }
}
