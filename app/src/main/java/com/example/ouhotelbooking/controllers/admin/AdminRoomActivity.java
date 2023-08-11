package com.example.ouhotelbooking.controllers.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
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
            roomAdapter = new RoomAdapter(this);
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
            Intent intent = EditRoomActivity.createIntent(this, 0, this.getIntent().getIntExtra(EXTRA_HOTEL, 0));
            startActivity(intent);
        });

    }
}
