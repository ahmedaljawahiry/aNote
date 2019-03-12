package com.ahmed.anote.displays.pinDisplay.deletePin;

import android.view.View;
import android.widget.ImageView;

import com.ahmed.anote.R;
import com.ahmed.anote.displays.pinDisplay.PinDisplayActivity;

import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DeleteButtonTest {

    @Test
    public void dialogIsShownOnClick() {
        PinDisplayActivity activityMock = mock(PinDisplayActivity.class);
        ImageView buttonMock = mock(ImageView.class);
        doReturn(buttonMock).when(activityMock).findViewById(eq(R.id.delete_pin_button));

        DeleteAlertDialog dialogMock = mock(DeleteAlertDialog.class);

        new DeleteButton(activityMock, dialogMock).onClick(mock(View.class));
        verify(dialogMock, times(1)).show();
    }
}
