package com.ahmed.anote.pinDisplay.deletePin;

import com.ahmed.anote.db.SQL;
import com.ahmed.anote.pinDisplay.PinDisplayActivity;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


public class DeleteAlertDialogTest {

    @Test
    public void illegalArgumentExceptionIfDbIsReadOnly() {
        SQL sqlMock = mock(SQL.class);
        doReturn(true).when(sqlMock).dbIsReadOnly();
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new DeleteAlertDialog(mock(PinDisplayActivity.class), sqlMock) )
                .withMessage("Input DB must be writable!");
    }

    // TODO: Test things happen on positive action. Difficult because of alertDialog...
}
