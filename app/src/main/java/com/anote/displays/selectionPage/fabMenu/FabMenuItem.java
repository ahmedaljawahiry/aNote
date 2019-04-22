package com.anote.displays.selectionPage.fabMenu;

import android.view.View;

public interface FabMenuItem {

    String PIN = "PinFab";
    String OTHER_NOTE = "NoteFab";

    void popUp(View view);
    void hide();
}
