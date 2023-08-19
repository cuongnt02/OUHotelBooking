package com.example.ouhotelbooking.controllers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.constants.UserRole;
import com.example.ouhotelbooking.controllers.admin.AdminActivity;
import com.example.ouhotelbooking.data.datasource.UserDataSource;
import com.example.ouhotelbooking.data.model.User;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private UserDataSource userDataSource;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        userDataSource = new UserDataSource(this);
        userDataSource.open();
        usernameEdit = findViewById(R.id.login_edit_text_username);
        passwordEdit = findViewById(R.id.login_edit_text_password);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(btn -> {
            login();
        });
    }

    private void login() {
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if (!validateUser(username, password)) {
            new AlertDialog.Builder(this)
                    .setTitle("Lỗi đăng nhập")
                    .setMessage("Tài khoản hoặc mât khẩu không hợp lệ")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        }
        else {
            // Add user to preferences
            SharedPreferences userPrefs = getSharedPreferences(getString(R.string.user_pref),
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = userPrefs.edit();
            editor.putInt("userId", user.getId());
            editor.putBoolean("active", true);
            editor.commit();
            // Go to Search Activity
            Intent intent;
            if (user.getUserRole().equals(UserRole.ADMIN.toString())) {
                intent = new Intent(this, AdminActivity.class);
            }
            else {
                intent = new Intent(this, SearchActivity.class);
            }
            startActivity(intent);
        }
    }

    private boolean validateUser(String username, String password) {
        user = userDataSource.getUser(username);
        if (!user.getPassword().equals(password)) {
            passwordEdit.setError("Password not valid!");
            return false;
        }
        else return true;
    }
}
