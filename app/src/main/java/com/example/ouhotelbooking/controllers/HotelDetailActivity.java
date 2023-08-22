package com.example.ouhotelbooking.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.data.datasource.HotelDataSource;
import com.example.ouhotelbooking.data.model.Hotel;
import com.example.ouhotelbooking.utils.DbBitmapUtil;

public class HotelDetailActivity extends AppCompatActivity {
    public static final String EXTRA_HOTEL = "com.ntc.ouhotelbooking.controllers.hotel";
    private TextView hotelDetailTitle;
    private TextView hotelDetailAddress;
    private Button hotelDetailButton;
    private ImageView hotelImageView;

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
        hotelImageView = findViewById(R.id.imageView);
        hotelDetailTitle = (TextView) findViewById(R.id.hotel_detail_title);
        hotelDetailAddress = (TextView) findViewById(R.id.hotel_detail_address);
        hotelDetailButton = (Button) findViewById(R.id.booking_button);
        hotelDetailTitle.setText(hotel.getName());
        hotelDetailAddress.setText(hotel.getDescription());
        hotelImageView.setImageBitmap(DbBitmapUtil.getImage(hotel.getPicture()));
        hotelDetailButton.setOnClickListener(btn -> {
            Intent intent = RoomChoiceActivity.createIntent(this, hotel.getId());
            startActivity(intent);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            SharedPreferences userPrefs = getSharedPreferences(getString(R.string.user_pref),
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = userPrefs.edit();
            editor.putBoolean("active", false);
            editor.commit();
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
