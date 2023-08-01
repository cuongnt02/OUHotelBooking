package com.example.ouhotelbooking.controllers.admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.adapters.HotelAdapter;
import com.example.ouhotelbooking.data.datasource.HotelDataSource;
import com.example.ouhotelbooking.data.model.Hotel;

import java.util.Arrays;
import java.util.List;

public class AdminHotelActivity extends AppCompatActivity {

    private RecyclerView hotelRecyclerView;
    private HotelDataSource hotelDataSource;
    private HotelAdapter adapter;
    private Button createHotelButton;
    @Override
    protected void onResume() {
        hotelDataSource.open();
        List<Hotel> hotels = hotelDataSource.getHotels();
        if (adapter == null) {
            adapter = new HotelAdapter(this, true);
        } else {
            adapter.notifyDataSetChanged();
        }
        adapter.setHotels(hotels);
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
        hotelRecyclerView = findViewById(R.id.admin_hotel_list);
        hotelDataSource = new HotelDataSource(this);
        hotelRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        createHotelButton = (Button) findViewById(R.id.admin_create_hotel);
        createHotelButton.setOnClickListener(btn -> {
            Intent intent = new Intent(this, EditHotelActivity.class);
            startActivity(intent);
        });
    }
}
