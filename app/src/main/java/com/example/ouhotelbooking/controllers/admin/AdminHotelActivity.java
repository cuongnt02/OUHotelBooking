package com.example.ouhotelbooking.controllers.admin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.adapters.HotelAdapter;
import com.example.ouhotelbooking.data.datasource.HotelDataSource;
import com.example.ouhotelbooking.data.model.Hotel;

import java.util.List;

public class AdminHotelActivity extends AppCompatActivity {

    private RecyclerView hotelRecyclerView;
    private HotelDataSource hotelDataSource;
    @Override
    protected void onResume() {
        hotelDataSource.open();
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
        hotelDataSource.open();
        List<Hotel> hotels = hotelDataSource.getHotels();
        HotelAdapter adapter = new HotelAdapter(this, true);
        adapter.setHotels(hotels);
        hotelRecyclerView.setAdapter(adapter);
        hotelRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
