package com.example.ouhotelbooking.controllers;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.adapters.HotelAdapter;
import com.example.ouhotelbooking.data.datasource.HotelDataSource;
import com.example.ouhotelbooking.data.datasource.RoomDataSource;
import com.example.ouhotelbooking.data.model.Hotel;
import com.example.ouhotelbooking.data.model.Room;

import java.util.List;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity {
    private HotelAdapter hotelAdapter;
    private HotelDataSource hotelDataSource;

    private RoomDataSource roomDataSource;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Hotel> hotels;

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
    protected void onStart() {
        hotelDataSource.open();
        roomDataSource.open();
        hotels = hotelDataSource.getHotels();
        updateList(hotels);
        super.onStart();
    }

    private void updateList(List<Hotel> hotelList) {
        if (hotelAdapter == null) {
            hotelAdapter = new HotelAdapter(this);
        }
        hotelAdapter.setHotels(hotelList);
        recyclerView.setAdapter(hotelAdapter);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        hotelDataSource = new HotelDataSource(this);
        roomDataSource = new RoomDataSource(this);
        recyclerView = (RecyclerView) findViewById(R.id.list_hotels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    updateList(hotels.stream().filter(hotel -> hotel.getName().contains(newText))
                            .collect(Collectors.toList()));
                    return true;
                }
                return false;
            }
        });
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
