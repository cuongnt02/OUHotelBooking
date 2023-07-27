package com.example.ouhotelbooking.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;

public class AuthActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_auth);

        loginButton = findViewById(R.id.auth_button_login);
        loginButton.setOnClickListener(btn -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        registerButton = findViewById(R.id.auth_button_register);
        registerButton.setOnClickListener(btn -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

    }


}