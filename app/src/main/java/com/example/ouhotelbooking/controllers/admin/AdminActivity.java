package com.example.ouhotelbooking.controllers.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin);
        Button hotelMngButton = (Button) findViewById(R.id.admin_hotel_button);
        Button userMngButton = (Button) findViewById(R.id.admin_user_button);
        userMngButton.setOnClickListener(btn -> {
            Intent intent = new Intent(this, AdminUserActivity.class);
            startActivity(intent);
        });
        hotelMngButton.setOnClickListener(btn -> {
            Intent intent = new Intent(this, AdminHotelActivity.class);
            startActivity(intent);
        });
    }
}
