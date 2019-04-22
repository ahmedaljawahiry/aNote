package com.anote.displays.selectionPage.pinsGrid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.anote.R;
import com.anote.auth.BiometricAuth;
import com.anote.db.Contract;
import com.anote.displays.selectionPage.NoteSelectionActivity;

import net.sqlcipher.Cursor;

public class ClickableTiles implements AdapterView.OnItemClickListener {

    private NoteSelectionActivity activity;
    private Intent intent;
    private BiometricAuth biometricAuth;
    private Cursor cursor;

    public ClickableTiles(
            NoteSelectionActivity activity,
            Cursor cursor,
            Intent intent,
            BiometricAuth biometricAuth
    ) {
        this.activity = activity;
        this.cursor = cursor;
        this.intent = intent;
        this.biometricAuth = biometricAuth;

        GridView gridview = activity.findViewById(R.id.pins_grid);
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        TextView key = view.findViewById(R.id.note_tile_main_text);
        TextView hint = view.findViewById(R.id.note_tile_sub_text);
        boolean isLocked =
                (cursor.getInt(cursor.getColumnIndex(Contract.Pins.COLUMN_SECURITY_LEVEL)) != 0);

        bundle.putString(Contract.Pins.COLUMN_KEY, key.getText().toString());
        bundle.putString(Contract.Pins.COLUMN_HINT, hint.getText().toString());
        intent.putExtras(bundle);

        if (isLocked) {
            biometricAuth.setIntent(intent);
            biometricAuth.authenticateUser();
        }
        else {
            activity.startActivity(intent);
        }
    }
}
