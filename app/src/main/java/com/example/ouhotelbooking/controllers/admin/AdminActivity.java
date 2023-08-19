package com.example.ouhotelbooking.controllers.admin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.AuthActivity;

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
        getSupportActionBar().setTitle("OU Hotel Booking - ADMIN");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_layout, menu);
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
        return super.onOptionsItemSelected(item);
    }
}
