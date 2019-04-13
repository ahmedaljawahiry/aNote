package com.ahmed.anote.displays.selectionPage.pinsGrid;

import android.content.Intent;
import android.database.Cursor;
import android.widget.GridView;

import com.ahmed.anote.R;
import com.ahmed.anote.displays.pinDisplay.PinDisplayActivity;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;

public class PinsGrid {

    private GridView gridview;

    public PinsGrid(NoteSelectionActivity activity, Cursor cursor) {
        this.gridview = activity.findViewById(R.id.pins_grid);

        new ClickableTiles(
                activity,
                new Intent(activity, PinDisplayActivity.class)
        );

        PinsAdapter pinsAdapter = new PinsAdapter(activity, cursor, 0);
        gridview.setAdapter(pinsAdapter);
    }
}
