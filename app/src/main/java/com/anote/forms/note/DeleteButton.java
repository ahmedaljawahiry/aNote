package com.anote.forms.note;

import android.view.View;
import android.widget.ImageView;

import com.anote.R;
import com.anote.common.dialogs.DeleteAlertDialog;
import com.anote.common.dialogs.DiscardAlertDialog;

public class DeleteButton implements View.OnClickListener {

    private ImageView button;
    private DeleteAlertDialog deleteDialog;
    private DiscardAlertDialog discardDialog;
    private boolean newNote;

    public DeleteButton(
            NoteFormActivity activity,
            DeleteAlertDialog deleteDialog,
            DiscardAlertDialog discardDialog,
            boolean newNote) {

        this.deleteDialog = deleteDialog;
        this.discardDialog = discardDialog;
        this.newNote = newNote;

        this.button = activity.findViewById(R.id.delete_note_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (newNote) {
            discardDialog.show();
        }
        else {
            deleteDialog.show();
        }
    }

}
