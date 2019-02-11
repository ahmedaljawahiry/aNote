package com.ahmed.anote.pinDisplay.editPin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.pinDisplay.PinDisplayActivity;
import com.ahmed.anote.pinDisplay.PinValues;
import com.ahmed.anote.pinForm.PinFormActivity;
import com.ahmed.anote.util.PinAttributes;

public class EditButton implements View.OnClickListener {

    private ImageView button;
    private PinValues values;

    public EditButton(PinDisplayActivity activity, PinValues values) {
        this.values = values;
        this.button = activity.findViewById(R.id.edit_pin_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        Intent intent = new Intent(context, PinFormActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(PinAttributes.KEY.name(), values.getKey());
        bundle.putString(PinAttributes.HINT.name(), values.getHint());
        bundle.putString(PinAttributes.PIN.name(), values.getPin());
        intent.putExtras(bundle);

        context.startActivity(intent);
    }
}
