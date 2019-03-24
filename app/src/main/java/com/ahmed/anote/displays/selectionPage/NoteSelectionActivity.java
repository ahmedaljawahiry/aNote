package com.ahmed.anote.displays.selectionPage;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ahmed.anote.R;
import com.ahmed.anote.db.DbHelper;
import com.ahmed.anote.db.sql.NoteSQL;
import com.ahmed.anote.db.sql.PinSQL;
import com.ahmed.anote.displays.selectionPage.fabMenu.FabMenu;
import com.ahmed.anote.displays.selectionPage.fabMenu.FabMenuItemFactory;
import com.ahmed.anote.displays.selectionPage.notesGrid.NotesGrid;
import com.ahmed.anote.displays.selectionPage.pinsGrid.PinsGrid;
import com.ahmed.anote.common.ToastPrinter;
import com.ahmed.anote.common.Util;

public class NoteSelectionActivity extends AppCompatActivity {

    final public static String EXIT_TOAST_MESSAGE = "Click back again to exit";

    private boolean doubleBackToExitPressedOnce = false;
    private FabMenu fabMenu;
    private ToastPrinter toastPrinter;
    private DbHelper dbHelper;
    private boolean appClosed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_selection);
        Util.setSecureFlags(this);

        this.fabMenu = new FabMenu(this, new FabMenuItemFactory());

        dbHelper = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        new PinsGrid(this, new PinSQL(db).GET_TABLE());
        new NotesGrid(this, new NoteSQL(db).GET_TABLE());
    }

    @Override
    public void onBackPressed() {
        if (fabMenu.isOpen()) {
            fabMenu.close();
        }
        else {
            if (doubleBackToExitPressedOnce) {
                closeApp();
            }
            else {
                this.doubleBackToExitPressedOnce = true;
                makeToastPrinter();
                toastPrinter.print(this, EXIT_TOAST_MESSAGE, Toast.LENGTH_SHORT);
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            }
        }
    }

    public void closeApp() {
        this.finishAffinity();
        dbHelper.close();
        appClosed = true;
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

    public void setDbHelper(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean appClosed() {
        return appClosed;
    }

}
