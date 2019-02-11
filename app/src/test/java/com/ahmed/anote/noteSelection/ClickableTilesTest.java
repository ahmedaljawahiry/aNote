package com.ahmed.anote.noteSelection;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ahmed.anote.R;

import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClickableTilesTest {

    @Test
    public void newActivityStartedOnItemClick() {
        Activity activityMock = createActivityMock();
        View viewMock = createViewMock();

        ClickableTiles clickableTiles = new ClickableTiles(activityMock);
        clickableTiles.onItemClick(mock(AdapterView.class), viewMock, 1, 1);

        verify(activityMock, times(1)).startActivity(any(Intent.class));
    }

    private Activity createActivityMock() {
        Activity activityMock = mock(Activity.class);
        doNothing().when(activityMock).startActivity(any(Intent.class));
        GridView gridViewMock = mock(GridView.class);
        doNothing().when(gridViewMock).setOnItemClickListener(any(ClickableTiles.class));
        doReturn(gridViewMock).when(activityMock).findViewById(eq(R.id.notes_grid));

        return activityMock;
    }

    private View createViewMock() {
        View viewMock = mock(View.class);
        TextView keyTextViewMock = mock(TextView.class);
        doReturn("KEY").when(keyTextViewMock).getText();
        doReturn(keyTextViewMock).when(viewMock).findViewById(eq(R.id.note_tile_key));

        TextView hintTextViewMock = mock(TextView.class);
        doReturn("HINT").when(hintTextViewMock).getText();
        doReturn(hintTextViewMock).when(viewMock).findViewById(eq(R.id.note_tile_hint));

        return viewMock;
    }
}
