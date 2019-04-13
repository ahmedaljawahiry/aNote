package com.ahmed.anote.displays.selectionPage.notesGrid;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.GridView;

import com.ahmed.anote.R;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;
import com.ahmed.anote.forms.note.NoteFormActivity;

public class NotesGrid {

    private GridView gridview;

    public NotesGrid(NoteSelectionActivity activity, Cursor cursor) {
        this.gridview = activity.findViewById(R.id.notes_grid);

        new ClickableTiles(
                activity,
                new Intent(activity, NoteFormActivity.class)
        );

        NotesAdapter notesAdapter = new NotesAdapter(activity, cursor, 0);
        gridview.setAdapter(notesAdapter);
    }
}
