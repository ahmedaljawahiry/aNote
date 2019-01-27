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
import com.ahmed.anote.util.ToastPrinter;

public class NoteSelectionActivity extends AppCompatActivity {

    final public static String EXIT_TOAST_MESSAGE = "Click back again to exit";

    private boolean doubleBackToExitPressedOnce = false;
    private FabMenu fabMenu;
    private ToastPrinter toastPrinter;
    private boolean finished;

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
                makeToastPrinter();
                toastPrinter.print(this, EXIT_TOAST_MESSAGE, Toast.LENGTH_SHORT);
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        finished = true;
    }

    private void makeToastPrinter() {
        if (toastPrinter == null) {
            toastPrinter = new ToastPrinter();
        }
    }

    public void setFabMenu(FabMenu fabMenu) {
        this.fabMenu = fabMenu;
    }

    public void setToastPrinter(ToastPrinter toastPrinter) {
        this.toastPrinter = toastPrinter;
    }

    public boolean isFinished() {
        return finished;
    }
}
