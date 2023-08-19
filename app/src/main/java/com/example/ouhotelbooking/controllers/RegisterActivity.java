package com.example.ouhotelbooking.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.constants.UserRole;
import com.example.ouhotelbooking.data.datasource.UserDataSource;
import com.example.ouhotelbooking.data.model.User;

import java.util.stream.Stream;

public class RegisterActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText confirmPasswordEdit;
    private EditText nameEdit;
    private EditText phoneEdit;
    private UserDataSource userDataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        userDataSource = new UserDataSource(this);
        usernameEdit = findViewById(R.id.register_edit_text_username);
        passwordEdit = findViewById(R.id.register_edit_text_password);
        confirmPasswordEdit = findViewById(R.id.register_edit_text_confirm_password);
        nameEdit = findViewById(R.id.register_edit_text_name);
        phoneEdit = findViewById(R.id.register_edit_text_phone);
        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register() {
        if (validateFields()) {
            userDataSource.open();
            User user = new User();
            user.setUsername(usernameEdit.getText().toString());
            user.setPassword(passwordEdit.getText().toString());
            user.setName(nameEdit.getText().toString());
            user.setPhoneNumber(phoneEdit.getText().toString());
            user.setUserRole(UserRole.USER.toString());
            User registeredUser = userDataSource.createUser(user);
            if (registeredUser == null) {
                new AlertDialog.Builder(this)
                        .setTitle("Register Failed")
                        .setMessage("There is something wrong with the database.")
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }

                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Register Success!")
                        .setMessage("You can use this account to login now.")
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(RegisterActivity.this, AuthActivity.class);
                                startActivity(intent);
                            }

                        })
                        .setIcon(android.R.drawable.ic_dialog_info).show();
            }
            userDataSource.close();
        }
    }

    private boolean validateFields() {
        return Stream.of(validateUsername(),
                validatePassword(),
                confirmPassword(),
                validateName(),
                validatePhoneNumber()).allMatch(Boolean.TRUE::equals);
    }

    private boolean validateUsername() {
        String username = usernameEdit.getText().toString();
        String error = null;
        if (username.isEmpty()) {
            error = "Trường này không được để trống.";
        }
        if (username.length() > 12) {
            error = "Tài khoản không được quá 12 kí tự.";
        }
        if (username.contains(" ")) {
            error = "Tên tài khoản không được có khoảng trắng";
        }
        usernameEdit.setError(error);
        return error == null;
    }

    private boolean validatePassword() {
        String password = passwordEdit.getText().toString();
        String error = null;
        if (password.isEmpty()) {
            error = "Trường này không được để trống";
        }
        if (password.length() <= 4) {
            error = "Mật khẩu phải lớn hơn 4 kí tự.";
        }
        passwordEdit.setError(error);
        return error == null;

    }

    private boolean confirmPassword() {
        String confirmPassword = confirmPasswordEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String error = null;
        if (confirmPassword.isEmpty()) {
            error = "Trường này không được để trống.";
        }
        if (!confirmPassword.equals(password)) {
            error = "Mật khẩu không khớp.";
        }
        confirmPasswordEdit.setError(error);
        return error == null;
    }

    private boolean validateName() {
        String name = nameEdit.getText().toString();
        String error = null;
        if (name.isEmpty()) {
            error = "Trường này không được để trống.";
        }
        if (name.length() > 50) {
            error = "Tên quá dài.";
        }
        nameEdit.setError(error);
        return error == null;
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = phoneEdit.getText().toString();
        String phoneValues = "\\d{10,11}";
        String error = null;
        if (phoneNumber.isEmpty()) {
            error = "Trường này không được để trống.";
        }
        if (!phoneNumber.matches(phoneValues)) {
            error = "Số điện thoại không hơp lệ";
        }
        phoneEdit.setError(error);
        return error == null;
    }

}
