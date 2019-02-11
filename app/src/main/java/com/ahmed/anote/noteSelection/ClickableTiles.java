package com.ahmed.anote.noteSelection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.pinDisplay.PinDisplayActivity;
import com.ahmed.anote.util.PinAttributes;

public class ClickableTiles implements AdapterView.OnItemClickListener {

    private Activity activity;

    public ClickableTiles(Activity activity) {
        this.activity = activity;

        GridView gridview = activity.findViewById(R.id.notes_grid);
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity, PinDisplayActivity.class);

        Bundle bundle = new Bundle();
        TextView key = view.findViewById(R.id.note_tile_key);
        TextView hint = view.findViewById(R.id.note_tile_hint);

        bundle.putString(PinAttributes.KEY.name(), key.getText().toString());
        bundle.putString(PinAttributes.HINT.name(), hint.getText().toString());
        intent.putExtras(bundle);

        activity.startActivity(intent);
    }
}
