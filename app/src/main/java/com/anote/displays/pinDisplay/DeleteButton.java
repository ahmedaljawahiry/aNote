package com.anote.displays.pinDisplay;

import android.view.View;
import android.widget.ImageView;

import com.anote.R;
import com.anote.common.dialogs.DeleteAlertDialog;

public class DeleteButton implements View.OnClickListener {

    private ImageView button;
    private DeleteAlertDialog dialog;

    public DeleteButton(PinDisplayActivity activity, DeleteAlertDialog dialog) {
        this.dialog = dialog;

        this.button = activity.findViewById(R.id.delete_pin_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dialog.show();
    }

}
