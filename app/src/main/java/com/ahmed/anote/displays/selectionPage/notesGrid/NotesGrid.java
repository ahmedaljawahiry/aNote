package com.ahmed.anote.displays.selectionPage.notesGrid;

import android.content.Intent;
import android.widget.GridView;

import com.ahmed.anote.R;
import com.ahmed.anote.auth.BiometricAuth;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;
import com.ahmed.anote.forms.note.NoteFormActivity;

import net.sqlcipher.Cursor;

public class NotesGrid {

    private GridView gridview;

    public NotesGrid(NoteSelectionActivity activity, Cursor cursor) {
        this.gridview = activity.findViewById(R.id.notes_grid);

        Intent intent = new Intent(activity, NoteFormActivity.class);
        BiometricAuth biometricAuth = new BiometricAuth(activity, intent);

        new ClickableTiles(activity, cursor, intent, biometricAuth);

        NotesAdapter notesAdapter = new NotesAdapter(activity, cursor, 0);
        gridview.setAdapter(notesAdapter);
    }
}
