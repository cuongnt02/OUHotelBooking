package com.example.ouhotelbooking.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.adapters.HotelAdapter;
import com.example.ouhotelbooking.data.datasource.HotelDataSource;
import com.example.ouhotelbooking.data.datasource.RoomDataSource;
import com.example.ouhotelbooking.data.model.Hotel;
import com.example.ouhotelbooking.data.model.Room;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private HotelAdapter hotelAdapter;
    private HotelDataSource hotelDataSource;

    private RoomDataSource roomDataSource;

    private RecyclerView recyclerView;

    @Override
    protected void onResume() {
        super.onResume();
        hotelDataSource.open();
        roomDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hotelDataSource.close();
        roomDataSource.close();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        hotelDataSource = new HotelDataSource(this);
        hotelDataSource.open();
        roomDataSource = new RoomDataSource(this);
        roomDataSource.open();
        List<Hotel> hotels = hotelDataSource.getHotels();
        recyclerView = (RecyclerView) findViewById(R.id.list_hotels);
        hotelAdapter = new HotelAdapter(this);
        hotelAdapter.setHotels(hotels);
        recyclerView.setAdapter(hotelAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
