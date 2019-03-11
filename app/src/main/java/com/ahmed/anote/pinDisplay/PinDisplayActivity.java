package com.ahmed.anote.pinDisplay;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ahmed.anote.R;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.db.SQL;
import com.ahmed.anote.noteSelection.ClickableTiles;
import com.ahmed.anote.pinDisplay.deletePin.DeleteAlertDialog;
import com.ahmed.anote.pinDisplay.deletePin.DeleteButton;
import com.ahmed.anote.pinDisplay.editPin.EditButton;
import com.ahmed.anote.util.PinAttributes;

public class PinDisplayActivity extends AppCompatActivity implements LifecycleObserver {

    private PinValues values;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_display);

        Bundle bundle = this.getIntent().getExtras();
        values = new PinValues();
        values.setKey(bundle.getString(PinAttributes.KEY.name()));
        values.setHint(bundle.getString(PinAttributes.HINT.name()));

        dbHelper = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        values.setPin(new SQL(db).GET_PIN_FROM_KEY(values.getKey()));

        createTextOnPage(R.id.key_display_text, values.getKey());
        createTextOnPage(R.id.hint_display_text, values.getHint());
        createTextOnPage(R.id.pin_display_text, values.getPin());

        new DeleteButton(this,
                new DeleteAlertDialog(this, new SQL(dbHelper.getWritableDatabase()))
        );
        new EditButton(this, values);

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onMoveToBackground() {
        dbHelper.close();
        this.finishAffinity();
    }

    private void createTextOnPage(int viewId, String displayText) {
        TextView text = this.findViewById(viewId);
        text.setText(displayText, TextView.BufferType.NORMAL);
        text.setEnabled(false);
        text.setTextColor(Color.BLACK);
    }

    public PinValues getValues() {
        return values;
    }
}
