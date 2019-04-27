package com.anote.displays.selectionPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.anote.forms.dbPassword.DbPasswordActivity;

import static com.anote.db.CipherDb.DB_PASSWORD_IS_SET_KEY;
import static com.anote.db.CipherDb.PREF_FILE_NAME;

public class NoteSelectionActivity extends ANoteActivity {

    final public static String EXIT_TOAST_MESSAGE = "Click back again to exit";

    private boolean doubleBackToExitPressedOnce = false;
    private FabMenu fabMenu;
    private CipherDb cipherDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_selection);

        SharedPreferences sharedPref = this.getSharedPreferences(
                PREF_FILE_NAME, Context.MODE_PRIVATE
        );
        boolean passwordSet = sharedPref.getBoolean(DB_PASSWORD_IS_SET_KEY, false);

        if (passwordSet) {
            cipherDb = getCipherDb();

            this.fabMenu = new FabMenu(this, new FabMenuItemFactory());

            new PinsGrid(this, new PinSQL(cipherDb).GET_TABLE());
            new NotesGrid(this, new NoteSQL(cipherDb).GET_TABLE());
        }
        else {
            Intent intent = new Intent(this, DbPasswordActivity.class);
            this.startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (fabMenu.isOpen()) {
            fabMenu.close();
        }
        else {
            if (doubleBackToExitPressedOnce) {
                this.finishAffinity();
            }
            else {
                this.doubleBackToExitPressedOnce = true;
                new ToastPrinter().print(this, EXIT_TOAST_MESSAGE, Toast.LENGTH_SHORT);
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            }
        }
    }
}
