package com.ahmed.anote.displays.selectionPage.pinsGrid;

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
        doReturn(mock(GridView.class)).when(activityMock).findViewById(eq(R.id.pins_grid));

        Intent intentMock = mock(Intent.class);

        ClickableTiles clickableTiles = new ClickableTiles(activityMock, intentMock);
        clickableTiles.onItemClick(mock(AdapterView.class), createViewMock(), 1, 1);

        verify(intentMock, times(1)).putExtras(any(Bundle.class));
        verify(activityMock, times(1)).startActivity(intentMock);
    }

    private View createViewMock() {
        View viewMock = mock(View.class);
        TextView keyTextViewMock = mock(TextView.class);
        doReturn("KEY").when(keyTextViewMock).getText();
        doReturn(keyTextViewMock).when(viewMock).findViewById(eq(R.id.note_tile_main_text));

        TextView hintTextViewMock = mock(TextView.class);
        doReturn("HINT").when(hintTextViewMock).getText();
        doReturn(hintTextViewMock).when(viewMock).findViewById(eq(R.id.note_tile_sub_text));

        return viewMock;
    }
}