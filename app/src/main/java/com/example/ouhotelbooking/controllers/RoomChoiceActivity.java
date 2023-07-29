package com.example.ouhotelbooking.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
        rooms = roomDataSource.getRooms(this.getIntent().getIntExtra(EXTRA_HOTEL, 0));

        recyclerView = (RecyclerView) findViewById(R.id.layout_room_list_list);
        roomAdapter = new RoomAdapter(this);
        roomAdapter.setRooms(rooms);
        recyclerView.setAdapter(roomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
