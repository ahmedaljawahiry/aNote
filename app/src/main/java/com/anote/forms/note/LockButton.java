package com.anote.forms.note;

import android.view.View;
import android.widget.ImageView;

import com.anote.R;

public class LockButton implements View.OnClickListener {

    private ImageView button;
    private InputValues inputValues;
    private boolean locked;

    public LockButton(NoteFormActivity activity, InputValues inputValues, boolean locked) {
        this.inputValues = inputValues;
        this.locked = locked;

        this.button = activity.findViewById(R.id.lock_note_button);
        button.setOnClickListener(this);

        if (locked) {
            button.setImageResource(R.drawable.ic_lock_closed_red_56dp);
        }
        inputValues.setLocked(locked);
    }

    @Override
    public void onClick(View v) {
        if (locked) {
            button.setImageResource(R.drawable.ic_lock_open_green_24dp);
            locked = false;
        }
        else {
            button.setImageResource(R.drawable.ic_lock_closed_red_56dp);
            locked = true;
        }
        inputValues.setLocked(locked);
    }
}
