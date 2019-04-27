package com.anote.db.encryption.activity;

import android.app.Activity;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.view.View;
import android.widget.Button;

import com.anote.R;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.KeyGenerator;

public class SaveButton implements View.OnClickListener {

    private Button button;

    public SaveButton(Activity activity) {
        this.button = activity.findViewById(R.id.save_note_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
                "alias",  KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build();
    }
}
