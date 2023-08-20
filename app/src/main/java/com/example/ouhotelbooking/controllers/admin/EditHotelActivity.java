package com.example.ouhotelbooking.controllers.admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.AuthActivity;
import com.example.ouhotelbooking.data.datasource.HotelDataSource;
import com.example.ouhotelbooking.data.model.Hotel;
import com.example.ouhotelbooking.utils.DbBitmapUtil;

import java.io.IOException;
import java.util.Arrays;

public class EditHotelActivity extends AppCompatActivity {

    public static final String EXTRA_HOTEL = "com.example.ouhotelbooking.hotel";
    public static final String TAG_DEBUG = "com.example.ouhotelbooking.debug";
    private EditText nameEditText;
    private EditText descriptionEditText;
    private HotelDataSource hotelDataSource;
    private Button editRoomButton;
    private ImageView imageView;
    private Bitmap bitmap;
    private Hotel hotel;


    public static Intent createIntent(Context packageContext, int hotelId) {
        Intent intent = new Intent(packageContext, EditHotelActivity.class);
        intent.putExtra(EXTRA_HOTEL, hotelId);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (imageView.getDrawable() != null)
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        hotelDataSource.open();
    }

    @Override
    protected void onStop() {
        if (hotel == null) {
            hotel = new Hotel();
        }
        hotel.setName(nameEditText.getText().toString());
        hotel.setDescription(descriptionEditText.getText().toString());
        if (bitmap != null)
            hotel.setPicture(DbBitmapUtil.getBytes(bitmap));

        super.onStop();
        hotelDataSource.close();
    }

    @Override
    protected void onDestroy() {
        hotelDataSource.open();
        if (hotel.getId() == 0) {
            hotelDataSource.createHotel(hotel);
        }
        else
            hotelDataSource.updateHotel(hotel);
        hotelDataSource.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_admin_hotel_edit);
        hotelDataSource = new HotelDataSource(this);
        hotelDataSource.open();
        nameEditText = (EditText) findViewById(R.id.admin_hotel_edit_name);
        descriptionEditText = (EditText) findViewById(R.id.admin_hotel_edit_description);
        imageView = (ImageView) findViewById(R.id.admin_hotel_edit_image);
        editRoomButton = (Button) findViewById(R.id.admin_hotel_room_edit);

        int id = this.getIntent().getIntExtra(EXTRA_HOTEL, 0);
        hotel = hotelDataSource.getHotel(id);
        if (id != 0) {
            nameEditText.setText(hotel.getName());
            bitmap = DbBitmapUtil.getImage(hotel.getPicture());
            imageView.setImageBitmap(bitmap);
        }
        editRoomButton.setOnClickListener(btn -> {
            Intent intent = AdminRoomActivity.createIntent(this, hotel.getId());
            startActivity(intent);
        });
        hotelDataSource.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                try {
                    this.bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu_edit, menu);
        if (this.getIntent().getIntExtra(EXTRA_HOTEL, 0) == 0) {
            MenuItem item = menu.findItem(R.id.action_delete);
            item.setVisible(false);
            invalidateOptionsMenu();
        }
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
        if (id == R.id.action_delete) {
            deleteHotel();
        }
        if (id == R.id.action_upload) {
            uploadImage();
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteHotel() {
        if (hotel != null) {
            hotelDataSource.deleteHotel(hotel);
        }
        finish();
    }

    public void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

}
