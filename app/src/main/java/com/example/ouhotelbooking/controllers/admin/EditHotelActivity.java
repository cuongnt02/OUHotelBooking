package com.example.ouhotelbooking.controllers.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.data.datasource.HotelDataSource;
import com.example.ouhotelbooking.data.model.Hotel;

public class EditHotelActivity extends AppCompatActivity {

    public static final String EXTRA_HOTEL = "com.example.ouhotelbooking.hotel";
    private EditText nameEditText;
    private EditText addressEditText;
    private HotelDataSource hotelDataSource;
    private Button deleteHotel;
    private Hotel hotel;



    public static Intent createIntent(Context packageContext, int hotelId) {
        Intent intent = new Intent(packageContext, EditHotelActivity.class);
        intent.putExtra(EXTRA_HOTEL, hotelId);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        hotelDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (hotel == null) {
            hotel = new Hotel();
            hotel.setName(nameEditText.getText().toString());
            hotel.setAddress(addressEditText.getText().toString());
            hotelDataSource.createHotel(hotel);
        }
        else {
            hotel.setName(nameEditText.getText().toString());
            hotel.setAddress(addressEditText.getText().toString());
            hotelDataSource.updateHotel(hotel);
        }
        hotelDataSource.close();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin_hotel_edit);
        hotelDataSource = new HotelDataSource(this);
        hotelDataSource.open();
        nameEditText = (EditText) findViewById(R.id.admin_hotel_edit_name);
        addressEditText = (EditText) findViewById(R.id.admin_hotel_edit_address);
        deleteHotel = (Button) findViewById(R.id.admin_hotel_delete);
        deleteHotel.setVisibility(View.INVISIBLE);

        int id =  this.getIntent().getIntExtra(EXTRA_HOTEL, 0);
        hotel = hotelDataSource.getHotel(id);
        if (id != 0) {
            nameEditText.setText(hotel.getName());
            addressEditText.setText(hotel.getAddress());
            deleteHotel.setVisibility(View.VISIBLE);
        }
        deleteHotel.setOnClickListener(btn -> {
            if (hotel != null) {
                hotelDataSource.deleteHotel(hotel);
            }
            finish();
        });
    }
}
