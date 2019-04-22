package com.anote.displays.selectionPage;

import android.os.Handler;
import android.widget.Toast;

import com.anote.db.CipherDb;
import com.anote.displays.selectionPage.fabMenu.FabMenu;
import com.anote.common.util.ToastPrinter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NoteSelectionActivityTest {

    private static FabMenu fabMenuMock;
    private static NoteSelectionActivity noteSelectionActivity;
    private static ToastPrinter toastPrinterMock;
    private static CipherDb cipherDbMock;

    @BeforeAll
    public static void setUp() {
        fabMenuMock = mock(FabMenu.class);
        toastPrinterMock = mock(ToastPrinter.class);
        cipherDbMock = mock(CipherDb.class);

        noteSelectionActivity = new NoteSelectionActivity();
        noteSelectionActivity.setFabMenu(fabMenuMock);
        noteSelectionActivity.setToastPrinter(toastPrinterMock);
        noteSelectionActivity.setCipherDb(cipherDbMock);
    }

    @Test
    public void fabMenuIsClosedOnBackPress() {
        doReturn(true).when(fabMenuMock).isOpen();
        noteSelectionActivity.onBackPressed();
        verify(fabMenuMock, times(1)).close();
    }

    @Test
    public void appShutOnDoubleBackPress() {
        doReturn(false).when(fabMenuMock).isOpen();
        noteSelectionActivity.onBackPressed();
        noteSelectionActivity.onBackPressed();

        assertThat(noteSelectionActivity.appClosed()).isTrue();
    }

    @Test
    public void toastDisplayedOnSingleBackPress() {
        doReturn(false).when(fabMenuMock).isOpen();
        noteSelectionActivity.onBackPressed();

        verify(toastPrinterMock, times(1))
                .print(eq(noteSelectionActivity),
                        eq(NoteSelectionActivity.EXIT_TOAST_MESSAGE), eq(Toast.LENGTH_SHORT));
    }

    @Test
    public void activityNotFinishedOnDelayedDoubleBackPress() {
        doReturn(false).when(fabMenuMock).isOpen();
        NoteSelectionActivity noteSelectionActivity = new NoteSelectionActivity();
        noteSelectionActivity.setFabMenu(fabMenuMock);
        noteSelectionActivity.setToastPrinter(toastPrinterMock);

        noteSelectionActivity.onBackPressed();
        new Handler().postDelayed(noteSelectionActivity::onBackPressed, 1000);
        assertThat(noteSelectionActivity.appClosed()).isFalse();
        /* TODO: The above assertion should fail. This test currently passes for any time (redundant)

            Cant use thread.sleep() since all threads are paused.
            Handler().postDelayed() doesn't work - onBackPressed is not called.
         */
    }
}


