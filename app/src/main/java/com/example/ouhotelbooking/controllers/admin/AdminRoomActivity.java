package com.example.ouhotelbooking.controllers.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.AuthActivity;
import com.example.ouhotelbooking.controllers.adapters.RoomAdapter;
import com.example.ouhotelbooking.data.datasource.HotelDataSource;
import com.example.ouhotelbooking.data.datasource.RoomDataSource;
import com.example.ouhotelbooking.data.model.Hotel;
import com.example.ouhotelbooking.data.model.Room;

import java.util.List;

public class AdminRoomActivity extends AppCompatActivity {
    private static final String EXTRA_HOTEL = "com.example.ouhotelbooking.room.hotel";
    private RecyclerView roomRecyclerView;
    private RoomAdapter roomAdapter;

    private HotelDataSource hotelDataSource;
    private RoomDataSource roomDataSource;
    private Button createRoomButton;

    public static Intent createIntent(Context packageContext,int hotelId) {
        Intent intent = new Intent(packageContext, AdminRoomActivity.class);
        intent.putExtra(EXTRA_HOTEL, hotelId);
        return intent;
    }

    @Override
    protected void onResume() {
        hotelDataSource.open();
        roomDataSource.open();
        Hotel hotel = hotelDataSource.getHotel(this.getIntent().getIntExtra(EXTRA_HOTEL, 0));
        List<Room> rooms = roomDataSource.getHotelRooms(hotel.getId());
        if (roomAdapter == null) {
            roomAdapter = new RoomAdapter(this, true);
        } else {
            roomAdapter.notifyDataSetChanged();
        }
        roomAdapter.setRooms(rooms);
        roomRecyclerView.setAdapter(roomAdapter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        hotelDataSource.close();
        roomDataSource.close();
        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin_room);
        roomRecyclerView = (RecyclerView) findViewById(R.id.admin_room_list);
        createRoomButton = (Button) findViewById(R.id.admin_room_create_button);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotelDataSource = new HotelDataSource(this);
        roomDataSource = new RoomDataSource(this);

        createRoomButton.setOnClickListener(btn -> {
            createRoom();
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
            createRoom();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createRoom() {
        Intent intent = EditRoomActivity.createIntent(this, 0, this.getIntent().getIntExtra(EXTRA_HOTEL, 0));
        startActivity(intent);
    }
}
