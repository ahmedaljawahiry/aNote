package com.anote.displays.pinDisplay;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.anote.R;

import org.junit.jupiter.api.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EditButtonTest {

    @Test
    public void activityStartedOnClick() {
        Intent intentMock = mock(Intent.class);

        PinDisplayActivity activityMock = mock(PinDisplayActivity.class);
        doReturn(mock(ImageView.class)).when(activityMock).findViewById(eq(R.id.edit_pin_button));

        EditButton editButton = new EditButton(activityMock, intentMock);
        editButton.onClick(mock(View.class));

        verify(activityMock, times(1)).startActivity(eq(intentMock));
    }
}
