package com.ahmed.anote.noteSelection.fabMenu;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.ahmed.anote.R;

public class FabMenu implements View.OnClickListener {

    private FloatingActionButton addButton;
    private FabMenuItem pinFab;
    private FabMenuItem otherNoteFab;

    private boolean open;

    public FabMenu(Activity activity, FabMenuItemFactory factory) {
        this.addButton = activity.findViewById(R.id.add_fab);
        addButton.setOnClickListener(this);

        this.pinFab = factory.getFab(FabMenuItem.PIN, activity);
        this.otherNoteFab = factory.getFab(FabMenuItem.OTHER_NOTE, activity);
    }

    @Override
    public void onClick(View view) {
        if (!open) {
            open(view);
        }
        else {
            close();
        }
    }

    public void open(View view) {
        open = true;
        pinFab.popUp(view);
        otherNoteFab.popUp(view);
    }

    public void close() {
        open = false;
        pinFab.hide();
        otherNoteFab.hide();
    }

    public boolean isOpen() {
        return open;
    }
}
