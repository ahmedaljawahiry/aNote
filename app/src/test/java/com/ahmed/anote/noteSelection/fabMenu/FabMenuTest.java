package com.ahmed.anote.noteSelection.fabMenu;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.ahmed.anote.R;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FabMenuTest {

    private static FabMenuItemFactory factoryMock;
    private static Activity activityMock;
    private static View viewMock;
    private static FloatingActionButton addButtonMock;
    private static PinFab pinFabMock;
    private static OtherNoteFab otherNoteFabMock;

    @BeforeClass
    public static void setUp() {
        pinFabMock = mock(PinFab.class);
        otherNoteFabMock = mock(OtherNoteFab.class);
        addButtonMock = mock(FloatingActionButton.class);

        activityMock = mock(Activity.class);
        viewMock = mock(View.class);
        doReturn(addButtonMock).when(activityMock).findViewById(eq(R.id.add_fab));
        doNothing().when(addButtonMock).setOnClickListener(any(View.OnClickListener.class));

        factoryMock = mock(FabMenuItemFactory.class);
        doReturn(pinFabMock).when(factoryMock).getFab(eq(FabMenuItem.PIN), eq(activityMock));
        doReturn(otherNoteFabMock).when(factoryMock).getFab(eq(FabMenuItem.OTHER_NOTE), eq(activityMock));
    }

    @Test
    public void bothFabsPopUpOnFirstClick() {
        FabMenu fabMenu = new FabMenu(activityMock, factoryMock);
        assertThat(fabMenu.isOpen()).isFalse();
        fabMenu.onClick(viewMock);
        verify(pinFabMock, times(1)).popUp(eq(viewMock));
        verify(otherNoteFabMock, times(1)).popUp(eq(viewMock));
    }

    @Test
    public void bothFabsHideOnSecondClick() {
        FabMenu fabMenu = new FabMenu(activityMock, factoryMock);
        fabMenu.onClick(viewMock);
        assertThat(fabMenu.isOpen()).isTrue();

        fabMenu.onClick(viewMock);
        verify(pinFabMock, times(1)).hide();
        verify(otherNoteFabMock, times(1)).hide();
        assertThat(fabMenu.isOpen()).isFalse();
    }
}
