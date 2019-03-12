package com.ahmed.anote.displays.selectionPage.pinsGrid;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;
import com.ahmed.anote.displays.selectionPage.fabMenu.FabMenu;

import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClickableTilesTest {

    private  FabMenu fabMenuMock = mock(FabMenu.class);

    @Test
    public void newActivityStartedOnItemClick() {
        NoteSelectionActivity activityMock = createActivityMock();
        View viewMock = createViewMock();

        doReturn(false).when(fabMenuMock).isOpen();
        ClickableTiles clickableTiles = new ClickableTiles(activityMock);
        clickableTiles.onItemClick(mock(AdapterView.class), viewMock, 1, 1);

        verify(activityMock, times(1)).startActivity(any(Intent.class));
    }

    @Test
    public void fabMenuClosedOnItemClick() {
        NoteSelectionActivity activityMock = createActivityMock();
        View viewMock = createViewMock();

        doReturn(true).when(fabMenuMock).isOpen();
        ClickableTiles clickableTiles = new ClickableTiles(activityMock);
        clickableTiles.onItemClick(mock(AdapterView.class), viewMock, 1, 1);

        verify(fabMenuMock, times(1)).close();
    }

    private NoteSelectionActivity createActivityMock() {
        NoteSelectionActivity activityMock = mock(NoteSelectionActivity.class);
        doReturn(fabMenuMock).when(activityMock).getFabMenu();

        GridView gridViewMock = mock(GridView.class);
        doReturn(gridViewMock).when(activityMock).findViewById(eq(R.id.pins_grid));

        return activityMock;
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
