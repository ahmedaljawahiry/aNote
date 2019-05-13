package com.anote.forms.dbPassword;

import android.os.Bundle;
import android.widget.Toast;

import com.anote.R;
import com.anote.common.activities.ANoteActivity;
import com.anote.common.util.ToastPrinter;
import com.anote.db.encryption.AndroidKeyStore;
import com.anote.db.encryption.Encrypter;

import java.security.KeyStoreException;

public class DbPasswordActivity extends ANoteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_password);

        ToastPrinter toastPrinter = new ToastPrinter();

        try {
            new SaveButton(
                    this,
                    new InputValues(this),
                    new Encrypter(
                            new AndroidKeyStore()),
                    toastPrinter
            );
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
            toastPrinter.print(this, "Failed to initialise KeyStore", Toast.LENGTH_LONG);
        }
    }
}
