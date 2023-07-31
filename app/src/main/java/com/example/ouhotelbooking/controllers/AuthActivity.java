package com.example.ouhotelbooking.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.admin.AdminActivity;

public class AuthActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    private Button adminButton;

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

        adminButton = findViewById(R.id.test_button_admin);
        adminButton.setOnClickListener(btn -> {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        });
    }


}