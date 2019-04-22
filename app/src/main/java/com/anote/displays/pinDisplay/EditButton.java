package com.anote.displays.pinDisplay;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.anote.R;

public class EditButton implements View.OnClickListener {

    private ImageView button;
    private PinDisplayActivity activity;
    private Intent intent;

    public EditButton(PinDisplayActivity activity, Intent intent) {
        this.activity = activity;
        this.intent = intent;
        this.button = activity.findViewById(R.id.edit_pin_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        activity.startActivity(intent);
    }
}
