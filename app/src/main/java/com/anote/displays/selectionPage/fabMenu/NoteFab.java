package com.anote.displays.selectionPage.fabMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anote.R;
import com.anote.forms.note.NoteFormActivity;

public class NoteFab implements FabMenuItem, View.OnClickListener {

    private LinearLayout layout;
    private TextView text;
    private FloatingActionButton button;

    public NoteFab(Activity activity) {
        this.layout = activity.findViewById(R.id.note_fab_layout);
        this.text = activity.findViewById(R.id.note_fab_text);
        this.button = activity.findViewById(R.id.note_fab);
        button.setOnClickListener(this);
    }

    public void popUp(View view) {
        layout.animate().translationY(-view.getResources().getDimension(R.dimen.second_fab_up));
        text.setVisibility(View.VISIBLE);
    }

    public void hide() {
        layout.animate().translationY(0);
        text.animate().translationY(0);
        text.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, NoteFormActivity.class);
        context.startActivity(intent);
    }
}
