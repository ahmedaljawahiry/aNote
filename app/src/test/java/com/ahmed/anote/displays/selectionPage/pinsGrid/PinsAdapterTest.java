package com.ahmed.anote.displays.selectionPage.pinsGrid;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.db.Contract;
import com.ahmed.anote.displays.selectionPage.notesGrid.NotesAdapter;

import org.junit.jupiter.api.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PinsAdapterTest {

    @Test
    public void lockInvisibleForUnlockedPins() {
        View viewMock = mock(View.class);
        Context contextMock = mock(Context.class);
        Cursor cursorMock = mock(Cursor.class);

        doReturn(3).when(cursorMock).getColumnIndex(eq(Contract.Pins.COLUMN_SECURITY_LEVEL));
        doReturn(0).when(cursorMock).getInt(eq(3));

        ImageView imageViewMock = mock(ImageView.class);
        doReturn(imageViewMock).when(viewMock).findViewById(eq(R.id.lock_image));
        doReturn(mock(TextView.class)).when(viewMock).findViewById(eq(R.id.note_tile_main_text));
        doReturn(mock(TextView.class)).when(viewMock).findViewById(eq(R.id.note_tile_sub_text));

        PinsAdapter adapter = new PinsAdapter(contextMock, cursorMock, 1);
        adapter.bindView(viewMock, contextMock, cursorMock);

        verify(imageViewMock, times(1)).setVisibility(eq(View.GONE));
    }
}
