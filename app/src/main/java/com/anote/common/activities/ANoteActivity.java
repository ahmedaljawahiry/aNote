package com.anote.common.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.anote.R;
import com.anote.common.util.ToastPrinter;
import com.anote.db.CipherDb;
import com.anote.db.encryption.DecrypterException;

public abstract class ANoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        this.getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        animateActionBar();

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

    private void animateActionBar() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        TextView actionBarText = findViewById(R.id.action_bar_text);
        TranslateAnimation animation = new TranslateAnimation(0.0f, width-150, 0.0f, 0.0f);

        animation.setDuration(10000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setFillAfter(false);
        actionBarText.startAnimation(animation);
    }
}
