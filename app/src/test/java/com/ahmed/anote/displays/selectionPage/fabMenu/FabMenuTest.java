package com.ahmed.anote.displays.selectionPage.fabMenu;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.ahmed.anote.R;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FabMenuTest {

    private static FabMenuItemFactory factoryMock;
    private static Activity activityMock;
    private static GridView pinsGridMock;
    private static GridView notesGridMock;
    private static RelativeLayout backgroundMock;
    private static View viewMock;
    private static FloatingActionButton addButtonMock;
    private static PinFab pinFabMock;
    private static NoteFab noteFabMock;


    @Before
    public void setUp() {
        pinFabMock = mock(PinFab.class);
        noteFabMock = mock(NoteFab.class);
        addButtonMock = mock(FloatingActionButton.class);
        pinsGridMock = mock(GridView.class);
        notesGridMock = mock(GridView.class);
        backgroundMock = mock(RelativeLayout.class);
        activityMock = mock(Activity.class);
        viewMock = mock(View.class);
        factoryMock = mock(FabMenuItemFactory.class);

        doReturn(addButtonMock).when(activityMock).findViewById(eq(R.id.add_fab));
        doReturn(mock(ViewPropertyAnimator.class)).when(addButtonMock).animate();

        doReturn(pinsGridMock).when(activityMock).findViewById(eq(R.id.pins_grid));
        doReturn(notesGridMock).when(activityMock).findViewById(eq(R.id.notes_grid));
        doReturn(backgroundMock).when(activityMock).findViewById(eq(R.id.fab_menu_background));

        doReturn(pinFabMock).when(factoryMock).getFab(eq(FabMenuItem.PIN), eq(activityMock));
        doReturn(noteFabMock).when(factoryMock).getFab(eq(FabMenuItem.OTHER_NOTE), eq(activityMock));
    }

    @Test
    public void bothFabsPopUpOnFirstClick() {
        FabMenu fabMenu = new FabMenu(activityMock, factoryMock);
        assertThat(fabMenu.isOpen()).isFalse();
        fabMenu.onClick(viewMock);
        verify(pinFabMock, times(1)).popUp(eq(viewMock));
        verify(noteFabMock, times(1)).popUp(eq(viewMock));
    }

    @Test
    public void bothFabsHideOnSecondClick() {
        FabMenu fabMenu = new FabMenu(activityMock, factoryMock);
        fabMenu.onClick(viewMock);
        assertThat(fabMenu.isOpen()).isTrue();

        fabMenu.onClick(viewMock);
        verify(pinFabMock, times(1)).hide();
        verify(noteFabMock, times(1)).hide();
        assertThat(fabMenu.isOpen()).isFalse();
    }

    @Test
    public void backgroundOpacityChangesOnClick() {
        FabMenu fabMenu = new FabMenu(activityMock, factoryMock);
        fabMenu.onClick(viewMock);
        verify(pinsGridMock, times(1)).setAlpha(eq(0.25f));
        verify(notesGridMock, times(1)).setAlpha(eq(0.25f));

        fabMenu.onClick(viewMock);
        verify(pinsGridMock, times(1)).setAlpha(eq(1f));
        verify(notesGridMock, times(1)).setAlpha(eq(1f));
    }

}
