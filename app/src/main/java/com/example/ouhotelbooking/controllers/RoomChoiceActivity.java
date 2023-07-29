package com.example.ouhotelbooking.controllers;

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
    private RoomAdapter roomAdapter;
    private RecyclerView recyclerView;
    private RoomDataSource roomDataSource;

    private List<Room> rooms;

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
        rooms = roomDataSource.getRooms();

        recyclerView = (RecyclerView) findViewById(R.id.layout_room_list_list);
        roomAdapter = new RoomAdapter(this);
        roomAdapter.setRooms(rooms);
        recyclerView.setAdapter(roomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
