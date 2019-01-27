package com.ahmed.anote.noteSelection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.pinDisplay.PinDisplayActivity;

public class ClickableTiles implements AdapterView.OnItemClickListener {

    private Activity activity;

    public ClickableTiles(Activity activity) {
        this.activity = activity;

        GridView gridview = activity.findViewById(R.id.notes_grid);
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity, PinDisplayActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("pin", "0000");
        intent.putExtras(bundle);

        activity.startActivity(intent);
    }
}
