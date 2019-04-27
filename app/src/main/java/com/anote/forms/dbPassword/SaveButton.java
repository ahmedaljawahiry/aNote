package com.anote.forms.dbPassword;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anote.R;
import com.anote.common.util.ToastPrinter;
import com.anote.db.CipherDb;
import com.anote.db.encryption.Encrypter;
import com.anote.db.encryption.EncrypterException;
import com.anote.displays.selectionPage.NoteSelectionActivity;


public class SaveButton implements View.OnClickListener {

    public static final String NOTHING_ENTERED_ERROR = "Enter a password!";
    public static final String MISMATCH_ERROR = "The two entries do not match!";
    public static final String ENCRYPTION_ERROR = "Error occurred during encryption";

    private Button button;
    private InputValues inputValues;
    private Encrypter encrypter;
    private ToastPrinter toastPrinter;

    public SaveButton(Activity activity, InputValues inputValues,
                      Encrypter encrypter, ToastPrinter toastPrinter) {
        this.inputValues = inputValues;
        this.encrypter = encrypter;
        this.toastPrinter = toastPrinter;
        this.button = activity.findViewById(R.id.save_note_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        inputValues.find();
        Context context = view.getContext();

        if (inputValues.nothingEntered()) {
            toastPrinter.print(context, NOTHING_ENTERED_ERROR, Toast.LENGTH_SHORT);
            return;
        }

        if (!inputValues.areValid()) {
            toastPrinter.print(context, MISMATCH_ERROR, Toast.LENGTH_SHORT);
            return;
        }

        try {
            storePasswordInSharedPref(context, encrypter.encrypt(
                    inputValues.getPassword())
            );
        }
        catch (EncrypterException e) {
            e.printStackTrace();
            toastPrinter.print(context, ENCRYPTION_ERROR, Toast.LENGTH_SHORT);
            return;
        }

        Intent intent = new Intent(context, NoteSelectionActivity.class);
        context.startActivity(intent);
    }

    private void storePasswordInSharedPref(Context context, String password) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                CipherDb.PREF_FILE_NAME, Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CipherDb.DB_PASSWORD_KEY, password);
        editor.putString(CipherDb.DB_PASSWORD_IV_KEY, encrypter.getIvString());
        editor.putBoolean(CipherDb.DB_PASSWORD_IS_SET_KEY, true);
        editor.commit();
    }
}
