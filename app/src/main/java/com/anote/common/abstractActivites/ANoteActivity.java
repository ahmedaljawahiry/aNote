package com.anote.common.abstractActivites;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.anote.common.util.ToastPrinter;
import com.anote.db.CipherDb;
import com.anote.db.encryption.DecrypterException;

public abstract class ANoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public CipherDb getCipherDb() {
        try {
            return CipherDb.getInstance(this);
        }
        catch (DecrypterException e) {
            e.printStackTrace();
            new ToastPrinter().print(this, CipherDb.DECRYPTION_ERROR, Toast.LENGTH_SHORT);
            this.finish();
            return null;
        }
    }
}
