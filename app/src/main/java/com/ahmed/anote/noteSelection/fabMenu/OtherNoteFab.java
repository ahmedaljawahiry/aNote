package com.ahmed.anote.noteSelection.fabMenu;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahmed.anote.R;

public class OtherNoteFab implements FabMenuItem {

    private LinearLayout layout;
    private TextView text;

    public OtherNoteFab(Activity activity) {
        this.layout = activity.findViewById(R.id.other_note_fab_layout);
        this.text = activity.findViewById(R.id.other_note_fab_text);
    }

    public void popUp(View view) {
        layout.animate().translationY(-view.getResources().getDimension(R.dimen.standard_105));
        text.setVisibility(View.VISIBLE);
    }

    public void hide() {
        layout.animate().translationY(0);
        text.animate().translationY(0);
        text.setVisibility(View.INVISIBLE);
    }
}
