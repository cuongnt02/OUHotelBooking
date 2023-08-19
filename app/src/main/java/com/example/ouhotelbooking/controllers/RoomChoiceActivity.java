package com.example.ouhotelbooking.controllers;

import android.app.Activity;
import android.content.Context;
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
import com.example.ouhotelbooking.controllers.adapters.RoomAdapter;
import com.example.ouhotelbooking.data.datasource.RoomDataSource;
import com.example.ouhotelbooking.data.model.Room;

import java.util.List;

public class RoomChoiceActivity extends AppCompatActivity {
    private static final String EXTRA_HOTEL = "com.example.ouhotelbooking.roomchoice.hotel";
    private RoomAdapter roomAdapter;
    private RecyclerView recyclerView;
    private RoomDataSource roomDataSource;

    private List<Room> rooms;

    public static Intent createIntent(Context packageContext, int hotelId) {
        Intent intent = new Intent(packageContext, RoomChoiceActivity.class);
        intent.putExtra(EXTRA_HOTEL, hotelId);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        roomDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        roomDataSource.close();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_room_list);
        roomDataSource = new RoomDataSource(this);
        roomDataSource.open();
        rooms = roomDataSource.getHotelRooms(this.getIntent().getIntExtra(EXTRA_HOTEL, 0));

        recyclerView = (RecyclerView) findViewById(R.id.layout_room_list_list);
        roomAdapter = new RoomAdapter(this);
        roomAdapter.setRooms(rooms);
        recyclerView.setAdapter(roomAdapter);
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
