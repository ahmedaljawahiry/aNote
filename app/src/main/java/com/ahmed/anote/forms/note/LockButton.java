package com.ahmed.anote.forms.note;

import android.view.View;
import android.widget.ImageView;

import com.ahmed.anote.R;

public class LockButton implements View.OnClickListener {

    private ImageView button;
    private UserInput userInput;
    private boolean locked;

    public LockButton(NoteFormActivity activity, UserInput userInput, boolean locked) {
        this.userInput = userInput;
        this.locked = locked;

        this.button = activity.findViewById(R.id.lock_note_button);
        button.setOnClickListener(this);

        if (locked) {
            button.setImageResource(R.drawable.ic_lock_closed_red_56dp);
        }
        userInput.setLocked(locked);
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
        userInput.setLocked(locked);
    }
}
