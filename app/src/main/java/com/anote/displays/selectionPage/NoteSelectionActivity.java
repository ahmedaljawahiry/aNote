package com.anote.displays.selectionPage;

import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;

import com.anote.R;
import com.anote.common.abstractActivites.ANoteActivity;
import com.anote.db.CipherDb;
import com.anote.db.sql.NoteSQL;
import com.anote.db.sql.PinSQL;
import com.anote.displays.selectionPage.fabMenu.FabMenu;
import com.anote.displays.selectionPage.fabMenu.FabMenuItemFactory;
import com.anote.displays.selectionPage.notesGrid.NotesGrid;
import com.anote.displays.selectionPage.pinsGrid.PinsGrid;
import com.anote.common.util.ToastPrinter;

public class NoteSelectionActivity extends ANoteActivity {

    final public static String EXIT_TOAST_MESSAGE = "Click back again to exit";

    private boolean doubleBackToExitPressedOnce = false;
    private FabMenu fabMenu;
    private ToastPrinter toastPrinter;
    private CipherDb cipherDb;
    private boolean appClosed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_selection);

        this.fabMenu = new FabMenu(this, new FabMenuItemFactory());

        cipherDb = CipherDb.getInstance(this);

        new PinsGrid(this, new PinSQL(cipherDb).GET_TABLE());
        new NotesGrid(this, new NoteSQL(cipherDb).GET_TABLE());
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

    public void setCipherDb(CipherDb cipherDb) {
        this.cipherDb = cipherDb;
    }

    public boolean appClosed() {
        return appClosed;
    }

}
