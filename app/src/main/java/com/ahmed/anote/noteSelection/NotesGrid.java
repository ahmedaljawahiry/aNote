package com.ahmed.anote.noteSelection;

import android.app.Activity;
import android.database.Cursor;
import android.widget.GridView;

import com.ahmed.anote.R;

public class NotesGrid {

    private GridView gridview;

    public NotesGrid(Activity activity, Cursor cursor) {
        this.gridview = activity.findViewById(R.id.notes_grid);

        new ClickableTiles(activity);

        NotesAdapter notesAdapter = new NotesAdapter(activity.getApplicationContext(), cursor, 0);
        gridview.setAdapter(notesAdapter);
    }
}
