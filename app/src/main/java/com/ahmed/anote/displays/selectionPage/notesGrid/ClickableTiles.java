package com.ahmed.anote.displays.selectionPage.notesGrid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.auth.BiometricAuth;
import com.ahmed.anote.db.Contract;
import com.ahmed.anote.displays.selectionPage.NoteSelectionActivity;

import net.sqlcipher.Cursor;

public class ClickableTiles implements AdapterView.OnItemClickListener {

    private NoteSelectionActivity activity;
    private Cursor cursor;
    private Intent intent;
    private BiometricAuth biometricAuth;

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

        GridView gridview = activity.findViewById(R.id.notes_grid);
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();

        TextView title = view.findViewById(R.id.note_tile_main_text);
        boolean isLocked =
                (cursor.getInt(cursor.getColumnIndex(Contract.Notes.COLUMN_SECURITY_LEVEL)) != 0);

        bundle.putString(Contract.Notes.COLUMN_TITLE, title.getText().toString());
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
