package com.example.ouhotelbooking.controllers.admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.AuthActivity;
import com.example.ouhotelbooking.controllers.adapters.HotelAdapter;
import com.example.ouhotelbooking.data.datasource.HotelDataSource;
import com.example.ouhotelbooking.data.model.Hotel;

import java.util.Arrays;
import java.util.List;

public class AdminHotelActivity extends AppCompatActivity {

    private RecyclerView hotelRecyclerView;
    private LinearLayout noHotelPrompt;
    private HotelDataSource hotelDataSource;
    private HotelAdapter adapter;
    private Button createHotelButton;
    private List<Hotel> hotels;

    @Override
    protected void onResume() {
        hotelDataSource.open();
        hotels = hotelDataSource.getHotels();
        if (hotels.size() < 1) noHotelPrompt.setVisibility(View.VISIBLE);
        else noHotelPrompt.setVisibility(View.INVISIBLE);
        if (adapter == null) {
            adapter = new HotelAdapter(this, true);
            adapter.setHotels(hotels);
        } else {
            adapter.setHotels(hotels);
        }
        hotelRecyclerView.setAdapter(adapter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        hotelDataSource.close();
        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin_hotel);
        noHotelPrompt = findViewById(R.id.admin_no_hotel_message);
        hotelRecyclerView = findViewById(R.id.admin_hotel_list);
        hotelDataSource = new HotelDataSource(this);
        hotelRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        createHotelButton = (Button) findViewById(R.id.admin_create_hotel);
        createHotelButton.setOnClickListener(btn -> {
            createHotel();
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_list_view_menu, menu);
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
        if (id == R.id.action_add) {
            createHotel();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createHotel() {
        Intent intent = EditHotelActivity.createIntent(this, 0);
        startActivity(intent);
    }
}
