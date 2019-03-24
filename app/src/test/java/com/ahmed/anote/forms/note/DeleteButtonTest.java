package com.ahmed.anote.forms.note;

import android.view.View;
import android.widget.ImageView;

import com.ahmed.anote.R;
import com.ahmed.anote.common.dialogs.DeleteAlertDialog;
import com.ahmed.anote.common.dialogs.DiscardAlertDialog;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DeleteButtonTest {

    private static NoteFormActivity activityMock = mock(NoteFormActivity.class);

    @BeforeClass
    public static void setUp() {
        doReturn(mock(ImageView.class))
                .when(activityMock)
                .findViewById(eq(R.id.delete_note_button));
    }

    @Test
    public void deleteDialogShownIfNotNewNote() {
        DeleteAlertDialog deleteDialogMock = mock(DeleteAlertDialog.class);
        DiscardAlertDialog discardDialogMock = mock(DiscardAlertDialog.class);

        DeleteButton deleteButton = new DeleteButton(
                activityMock, deleteDialogMock, discardDialogMock, false
        );

        deleteButton.onClick(mock(View.class));

        verify(deleteDialogMock, times(1)).show();
        verify(discardDialogMock, times(0)).show();
    }

    @Test
    public void discardDialogShownIfNewNote() {
        DeleteAlertDialog deleteDialogMock = mock(DeleteAlertDialog.class);
        DiscardAlertDialog discardDialogMock = mock(DiscardAlertDialog.class);

        DeleteButton deleteButton = new DeleteButton(
                activityMock, deleteDialogMock, discardDialogMock, true
        );

        deleteButton.onClick(mock(View.class));

        verify(deleteDialogMock, times(0)).show();
        verify(discardDialogMock, times(1)).show();
    }
}
