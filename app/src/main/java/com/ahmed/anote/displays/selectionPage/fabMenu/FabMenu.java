package com.ahmed.anote.displays.selectionPage.fabMenu;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.GridView;

import com.ahmed.anote.R;

public class FabMenu implements View.OnClickListener {

    private FloatingActionButton addButton;
    private FabMenuItem pinFab;
    private FabMenuItem otherNoteFab;

    private GridView pinsGrid;
    private GridView notesGrid;

    private boolean open;

    public FabMenu(Activity activity, FabMenuItemFactory factory) {
        this.addButton = activity.findViewById(R.id.add_fab);
        addButton.setOnClickListener(this);

        this.pinsGrid = activity.findViewById(R.id.pins_grid);
        this.notesGrid = activity.findViewById(R.id.notes_grid);
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
        addButton.animate().rotationBy(45f);
        pinsGrid.setAlpha(0.25f);
        notesGrid.setAlpha(0.25f);
        pinFab.popUp(view);
        otherNoteFab.popUp(view);
    }

    public void close() {
        open = false;
        addButton.animate().rotationBy(45f);
        pinsGrid.setAlpha(1f);
        notesGrid.setAlpha(1f);
        pinFab.hide();
        otherNoteFab.hide();
    }

    public boolean isOpen() {
        return open;
    }
}
