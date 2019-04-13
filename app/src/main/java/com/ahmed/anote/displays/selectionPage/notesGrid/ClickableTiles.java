package com.ahmed.anote.displays.selectionPage.notesGrid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.db.Contract;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;
import com.ahmed.anote.forms.note.NoteFormActivity;

public class ClickableTiles implements AdapterView.OnItemClickListener {

    private NoteSelectionActivity activity;
    private Intent intent;

    public ClickableTiles(NoteSelectionActivity activity, Intent intent) {
        this.activity = activity;
        this.intent = intent;

        GridView gridview = activity.findViewById(R.id.notes_grid);
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        TextView title = view.findViewById(R.id.note_tile_main_text);

        bundle.putString(Contract.Notes.COLUMN_TITLE, title.getText().toString());
        intent.putExtras(bundle);

        activity.startActivity(intent);
    }
}
