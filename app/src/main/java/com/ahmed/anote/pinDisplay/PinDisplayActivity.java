package com.ahmed.anote.pinDisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmed.anote.R;

public class PinDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_display);

        Bundle bundle = this.getIntent().getExtras();

        EditText editText = this.findViewById(R.id.pin_display_text);
        editText.setText(bundle.getString("pin"), TextView.BufferType.EDITABLE);
    }
}
