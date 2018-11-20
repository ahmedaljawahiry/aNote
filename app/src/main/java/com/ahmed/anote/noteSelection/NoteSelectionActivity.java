package com.ahmed.anote.noteSelection;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.db.Contract;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.noteSelection.fabMenu.FabMenu;
import com.ahmed.anote.noteSelection.fabMenu.FabMenuItemFactory;
import com.ahmed.anote.noteSelection.fabMenu.OtherNoteFab;
import com.ahmed.anote.noteSelection.fabMenu.PinFab;
import com.ahmed.anote.util.ToastPrinter;

public class NoteSelectionActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private FabMenu fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_selection);

        this.fabMenu = new FabMenu(this, new FabMenuItemFactory());
        new NotesGrid(this, generateCursor());
    }

    private Cursor generateCursor() {
        DbHelper dbHelper = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(Contract.Pins.TABLE_NAME, null, null, null, null, null, null);
    }

    @Override
    public void onBackPressed() {
        if (fabMenu.isOpen()) {
            fabMenu.close();
        }
        else {
            if (doubleBackToExitPressedOnce) {
                this.finish(); // TODO: Exit application.
            }
            else {
                this.doubleBackToExitPressedOnce = true;
                new ToastPrinter().print(this, "Click back again to exit", Toast.LENGTH_SHORT);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

}
