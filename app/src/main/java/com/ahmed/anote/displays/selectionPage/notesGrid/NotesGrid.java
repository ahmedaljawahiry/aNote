package com.ahmed.anote.displays.selectionPage.notesGrid;

import android.database.Cursor;
import android.widget.GridView;

import com.ahmed.anote.R;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;

public class NotesGrid {

    private GridView gridview;

    public NotesGrid(NoteSelectionActivity activity, Cursor cursor) {
        this.gridview = activity.findViewById(R.id.notes_grid);

        new ClickableTiles(activity);

        NotesAdapter notesAdapter = new NotesAdapter(activity.getApplicationContext(), cursor, 0);
        gridview.setAdapter(notesAdapter);
    }
}
