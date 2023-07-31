package com.example.ouhotelbooking.controllers.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;

public class EditHotelActivity extends AppCompatActivity {

    public static final String EXTRA_HOTEL = "com.example.ouhotelbooking.hotel";


    public static Intent createIntent(Context packageContext, int hotelId) {
        Intent intent = new Intent(packageContext, EditHotelActivity.class);
        intent.putExtra(EXTRA_HOTEL, hotelId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin_hotel_edit);
    }
}
