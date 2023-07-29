package com.example.ouhotelbooking.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.data.datasource.RoomDataSource;
import com.example.ouhotelbooking.data.model.Room;

import org.w3c.dom.Text;

public class RoomDetailActivity extends AppCompatActivity {
    public static final String EXTRA_ROOM = "com.example.ouhotelbooking.roomdetail";

    private TextView roomDetailTitle;
    private TextView roomDetailDescription;

    private RoomDataSource roomDataSource;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        roomDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        roomDataSource.close();
    }

    public static Intent createIntent(Context packageContext, int roomId) {
        Intent intent = new Intent(packageContext, RoomDetailActivity.class);
        intent.putExtra(EXTRA_ROOM, roomId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_room_detail);
        roomDataSource = new RoomDataSource(this);
        roomDataSource.open();
        Room room = roomDataSource.getRoom(this.getIntent().getIntExtra(EXTRA_ROOM, 0));
        roomDetailTitle = (TextView) findViewById(R.id.room_detail_type);
        roomDetailDescription = (TextView) findViewById(R.id.room_detail_description);
        roomDetailTitle.setText(room.getType());
        roomDetailDescription.setText(room.getDescription());

    }

}
