package com.example.ouhotelbooking.controllers.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.data.datasource.UserDataSource;
import com.example.ouhotelbooking.data.model.User;

public class EditUserActivity extends AppCompatActivity {
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText emailEdit;
    private Spinner userRoleSpinner;
    private UserDataSource userDataSource;
    private User user;

    private static final String EXTRA_USER_ID = "com.example.ouhotelbooking.user";

    public static Intent createIntent(Context packageContext, int userId) {
        Intent intent = new Intent(packageContext, EditUserActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        userDataSource.open();
        user = userDataSource.getUser(this.getIntent().getIntExtra(EXTRA_USER_ID, 0));
        if (user == null) {
            user = new User();
        } else {
            usernameEdit.setText(user.getUsername());
            passwordEdit.setText(user.getPassword());
            nameEdit.setText(user.getName());
            phoneEdit.setText(user.getPhoneNumber());
            emailEdit.setText(user.getEmail());
            userRoleSpinner.setSelection(user.getUserRole().equals("User") ? 0 : 1);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        userDataSource.open();
        user.setUsername(usernameEdit.getText().toString());
        user.setPassword(passwordEdit.getText().toString());
        user.setName(nameEdit.getText().toString());
        user.setPhoneNumber(phoneEdit.getText().toString());
        user.setEmail(emailEdit.getText().toString());
        user.setUserRole(userRoleSpinner.getSelectedItem().toString());

    }

    @Override
    protected void onDestroy() {
        if (user.getId() == 0) {
            userDataSource.createUser(user);
        } else userDataSource.updateUser(user);
        userDataSource.close();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        userDataSource.close();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin_user_edit);
        userDataSource = new UserDataSource(this);
        usernameEdit = findViewById(R.id.admin_user_edit_username);
        passwordEdit = findViewById(R.id.admin_user_edit_password);
        nameEdit = findViewById(R.id.admin_user_edit_name);
        phoneEdit = findViewById(R.id.admin_user_edit_phone);
        emailEdit = findViewById(R.id.admin_user_edit_email);
        userRoleSpinner = findViewById(R.id.admin_user_edit_role);
        ArrayAdapter<String> userRoles = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"User", "Admin"});
        userRoleSpinner.setAdapter(userRoles);

    }
}
