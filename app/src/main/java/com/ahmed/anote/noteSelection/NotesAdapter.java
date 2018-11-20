package com.ahmed.anote.noteSelection;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.db.Contract;

public class NotesAdapter extends CursorAdapter {

    public final static int TILE_SIZE = 400;

    public NotesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_notes_grid_tile, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String key = cursor.getString(cursor.getColumnIndex(Contract.Pins.COLUMN_KEY));
        String hint = cursor.getString(cursor.getColumnIndex(Contract.Pins.COLUMN_HINT));

        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, TILE_SIZE));

        TextView title = view.findViewById(R.id.note_tile_key);
        title.setText(key);

        TextView moreInfo = view.findViewById(R.id.note_tile_hint);
        moreInfo.setText(hint);
    }
}
