package com.example.ouhotelbooking.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.data.datasource.RoomDataSource;
import com.example.ouhotelbooking.data.model.Room;

import org.w3c.dom.Text;

public class BookingDateActivity extends AppCompatActivity {
    private static final String EXTRA_ROOM = "com.example.ouhotelbooking.booking.room";
    private RoomDataSource roomDataSource;
    private Button confirmDateButton;
    private TextView testTextView;


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

    public static Intent createIntent(Context packageContext, int roomId) {
        Intent intent = new Intent(packageContext, BookingDateActivity.class);
        intent.putExtra(EXTRA_ROOM, roomId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booking_pick_date);

        roomDataSource = new RoomDataSource(this);
        roomDataSource.open();
        Room room = roomDataSource.getRoom(this.getIntent().getIntExtra(EXTRA_ROOM, 0));
        confirmDateButton = (Button) findViewById(R.id.layout_booking_confirm_date);
        testTextView = (TextView) findViewById(R.id.test_room_id);
        confirmDateButton.setOnClickListener(btn -> {
            Intent intent = new Intent(this, BookingDetailActivity.class);
            startActivity(intent);
        });
        testTextView.setText( "Room ID: "+ Integer.toString(room.getId()));

    }
}
