package com.ahmed.anote.displays.selectionPage.pinsGrid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.displays.pinDisplay.PinDisplayActivity;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;
import com.ahmed.anote.util.PinAttributes;

public class ClickableTiles implements AdapterView.OnItemClickListener {

    private NoteSelectionActivity activity;

    public ClickableTiles(NoteSelectionActivity activity) {
        this.activity = activity;

        GridView gridview = activity.findViewById(R.id.pins_grid);
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity, PinDisplayActivity.class);

        Bundle bundle = new Bundle();
        TextView key = view.findViewById(R.id.note_tile_main_text);
        TextView hint = view.findViewById(R.id.note_tile_sub_text);

        bundle.putString(PinAttributes.KEY.name(), key.getText().toString());
        bundle.putString(PinAttributes.HINT.name(), hint.getText().toString());
        intent.putExtras(bundle);

        activity.startActivity(intent);
    }
}
