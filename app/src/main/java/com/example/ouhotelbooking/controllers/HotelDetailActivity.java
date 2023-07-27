package com.example.ouhotelbooking.controllers;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;

public class HotelDetailActivity extends AppCompatActivity {
    public static final String EXTRA_HOTEL = "com.ntc.ouhotelbooking.controllers.hotel";
    private TextView hotelDetailTitle;
    private TextView hotelDetailAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hotel_detail);
        hotelDetailTitle = (TextView) findViewById(R.id.hotel_detail_title);
        hotelDetailAddress = (TextView) findViewById(R.id.hotel_detail_address);
    }
}
