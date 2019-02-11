package com.ahmed.anote.noteSelection.fabMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.pinForm.PinFormActivity;

public class PinFab implements FabMenuItem, View.OnClickListener {

    private LinearLayout layout;
    private TextView text;
    private FloatingActionButton button;

    public PinFab(Activity activity) {
        this.layout = activity.findViewById(R.id.pin_fab_layout);
        this.text = activity.findViewById(R.id.pin_fab_text);
        this.button = activity.findViewById(R.id.pin_fab);
        button.setOnClickListener(this);
    }

    public void popUp(View view) {
        layout.animate().translationY(-view.getResources().getDimension(R.dimen.standard_55));
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
        Intent intent = new Intent(context, PinFormActivity.class);
        context.startActivity(intent);
    }

}
