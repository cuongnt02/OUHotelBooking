package com.example.ouhotelbooking.controllers.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.AuthActivity;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu_edit, menu);
        if (this.getIntent().getIntExtra(EXTRA_USER_ID, 0) == 0) {
            MenuItem item = menu.findItem(R.id.action_delete);
            item.setVisible(false);
            invalidateOptionsMenu();
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            SharedPreferences userPrefs = getSharedPreferences(getString(R.string.user_pref),
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = userPrefs.edit();
            editor.putBoolean("active", false);
            editor.commit();
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_delete) {
            deleteUser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteUser() {
        if (user != null) {
            userDataSource.deleteUser(user);
        }
        finish();
    }
}
