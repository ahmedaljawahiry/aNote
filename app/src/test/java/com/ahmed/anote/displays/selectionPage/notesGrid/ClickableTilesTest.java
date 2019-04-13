package com.ahmed.anote.displays.selectionPage.notesGrid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;

import org.junit.jupiter.api.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClickableTilesTest {

    @Test
    public void activityStartedWithBundleOnClick() {
        NoteSelectionActivity activityMock = mock(NoteSelectionActivity.class);
        doReturn(mock(GridView.class)).when(activityMock).findViewById(eq(R.id.notes_grid));

        Intent intentMock = mock(Intent.class);

        ClickableTiles clickableTiles = new ClickableTiles(activityMock, intentMock);
        clickableTiles.onItemClick(mock(AdapterView.class), createViewMock(), 1, 1);

        verify(intentMock, times(1)).putExtras(any(Bundle.class));
        verify(activityMock, times(1)).startActivity(intentMock);
    }

    private View createViewMock() {
        View viewMock = mock(View.class);
        TextView textViewMock = mock(TextView.class);
        doReturn("TITLE").when(textViewMock).getText();
        doReturn(textViewMock).when(viewMock).findViewById(eq(R.id.note_tile_main_text));

        return viewMock;
    }
}
