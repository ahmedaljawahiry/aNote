package com.ahmed.anote.displays.selectionPage.notesGrid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.displays.pinDisplay.PinDisplayActivity;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;
import com.ahmed.anote.forms.note.NoteFormActivity;
import com.ahmed.anote.util.NoteAttributes;
import com.ahmed.anote.util.PinAttributes;

public class ClickableTiles implements AdapterView.OnItemClickListener {

    private NoteSelectionActivity activity;

    public ClickableTiles(NoteSelectionActivity activity) {
        this.activity = activity;

        GridView gridview = activity.findViewById(R.id.notes_grid);
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity, NoteFormActivity.class);

        Bundle bundle = new Bundle();
        TextView title = view.findViewById(R.id.note_tile_main_text);

        bundle.putString(NoteAttributes.TITLE.name(), title.getText().toString());
        intent.putExtras(bundle);

        activity.startActivity(intent);
    }
}
