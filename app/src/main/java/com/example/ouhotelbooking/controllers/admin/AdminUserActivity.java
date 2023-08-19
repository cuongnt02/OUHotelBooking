package com.example.ouhotelbooking.controllers.admin;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.adapters.UserAdapter;
import com.example.ouhotelbooking.data.datasource.UserDataSource;
import com.example.ouhotelbooking.data.model.User;

import java.util.List;

public class AdminUserActivity extends AppCompatActivity {

    private RecyclerView userRecyclerView;
    private UserDataSource userDataSource;
    private Button createUserButton;
    private UserAdapter userAdapter;

    @Override
    protected void onResume() {
        userDataSource.open();
        List<User> users = userDataSource.getUsers();
        if (userAdapter == null) {
            userAdapter = new UserAdapter(this);
        }
        else {
            userAdapter.notifyDataSetChanged();
        }
        userAdapter.setUsers(users);
        userRecyclerView.setAdapter(userAdapter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        userDataSource.close();
        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin_user);
        userDataSource = new UserDataSource(this);
        userRecyclerView = findViewById(R.id.admin_user_list);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        createUserButton = findViewById(R.id.admin_user_create);
        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = EditUserActivity.createIntent(AdminUserActivity.this, 0);
                startActivity(intent);
            }
        });

    }
}
