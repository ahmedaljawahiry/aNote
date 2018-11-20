package com.ahmed.anote.noteSelection.fabMenu;

import android.view.View;

public interface FabMenuItem {

    String PIN = "PinFab";
    String OTHER_NOTE = "OtherNoteFab";

    void popUp(View view);
    void hide();
}
