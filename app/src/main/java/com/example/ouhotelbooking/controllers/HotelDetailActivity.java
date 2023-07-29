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
import com.example.ouhotelbooking.data.datasource.HotelDataSource;
import com.example.ouhotelbooking.data.model.Hotel;

public class HotelDetailActivity extends AppCompatActivity {
    public static final String EXTRA_HOTEL = "com.ntc.ouhotelbooking.controllers.hotel";
    private TextView hotelDetailTitle;
    private TextView hotelDetailAddress;
    private Button hotelDetailButton;

    public static Intent createIntent(Context packageContext, int hotelId) {
        Intent intent = new Intent(packageContext, HotelDetailActivity.class);
        intent.putExtra(EXTRA_HOTEL, hotelId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hotel_detail);
        HotelDataSource hotelDataSource = new HotelDataSource(this);
        hotelDataSource.open();
        Hotel hotel = hotelDataSource.getHotel(this.getIntent().getIntExtra(EXTRA_HOTEL, 0));
//        hotelDataSource.close();
        hotelDetailTitle = (TextView) findViewById(R.id.hotel_detail_title);
        hotelDetailAddress = (TextView) findViewById(R.id.hotel_detail_address);
        hotelDetailButton = (Button) findViewById(R.id.booking_button);
        hotelDetailTitle.setText(hotel.getName());
        hotelDetailAddress.setText(hotel.getAddress());
        hotelDetailButton.setOnClickListener(btn -> {
            Intent intent = new Intent(this , RoomChoiceActivity.class);
            startActivity(intent);
        });
    }
}
