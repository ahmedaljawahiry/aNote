package com.ahmed.anote.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahmed.anote.R;
import com.ahmed.anote.util.ToastPrinter;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new EnterButton(this, new PasswordChecker(), new ToastPrinter());
    }

}

