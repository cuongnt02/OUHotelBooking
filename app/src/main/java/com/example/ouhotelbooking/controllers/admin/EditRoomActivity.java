package com.example.ouhotelbooking.controllers.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.constants.RoomType;
import com.example.ouhotelbooking.controllers.AuthActivity;
import com.example.ouhotelbooking.data.datasource.RoomDataSource;
import com.example.ouhotelbooking.data.model.Room;
import com.example.ouhotelbooking.utils.DbBitmapUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class EditRoomActivity extends AppCompatActivity {
    private static final String EXTRA_ROOM = "com.example.ouhotelbooking.admin.room";
    private static final String EXTRA_ROOM_HOTEL = "com.example.ouhotelbooking.admin.roomhotel";

    private EditText roomTitleEdit;
    private EditText roomDescriptionEdit;
    private EditText roomPriceEdit;
    private Spinner roomTypeSpinner;
    private ImageView roomImage;
    private Room room;
    private RoomDataSource roomDataSource;
    private Bitmap bitmap;

    public static Intent createIntent(Context packageContext, int roomId, int hotelId) {
        Intent intent = new Intent(packageContext, EditRoomActivity.class);
        intent.putExtra(EXTRA_ROOM, roomId);
        intent.putExtra(EXTRA_ROOM_HOTEL, hotelId);
        return intent;
    }

    @Override
    protected void onPause() {
        if (room == null) {
            room = new Room();
        }
        room.setTitle(roomTitleEdit.getText().toString());
        room.setDescription(roomDescriptionEdit.getText().toString());
        room.setPrice(Double.parseDouble(roomPriceEdit.getText().toString()));
        room.setPicture(DbBitmapUtil.getBytes(bitmap));
        room.setType(roomTypeSpinner.getSelectedItem().toString());
        room.setHotelId(this.getIntent().getIntExtra(EXTRA_ROOM_HOTEL, 0));
        if (bitmap != null) {
            room.setPicture(DbBitmapUtil.getBytes(bitmap));
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (room.getId() == 0) {
            roomDataSource.createRoom(room);
        }
        else roomDataSource.updateRoom(room);
        roomDataSource.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (roomImage.getDrawable() != null) {
            bitmap = ((BitmapDrawable)roomImage.getDrawable()).getBitmap();
        }
        roomDataSource.open();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin_room_edit);
        roomDataSource = new RoomDataSource(this);
        List<String> roomTypes = EnumSet.allOf(RoomType.class)
                .stream().map(RoomType::toString).collect(Collectors.toList());

        roomTitleEdit = (EditText) findViewById(R.id.admin_room_room_title);
        roomDescriptionEdit = (EditText) findViewById(R.id.admin_room_room_desc);
        roomPriceEdit = (EditText) findViewById(R.id.admin_room_room_price);
        roomTypeSpinner = (Spinner) findViewById(R.id.admin_room_room_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                roomTypes
        );
        roomTypeSpinner.setAdapter(adapter);
        roomImage = (ImageView) findViewById(R.id.admin_room_room_image);
        roomDataSource.open();

        int roomId = this.getIntent().getIntExtra(EXTRA_ROOM, 0);
        room = roomDataSource.getRoom(roomId);
        if (roomId != 0) {
            roomTitleEdit.setText(room.getTitle());
            roomDescriptionEdit.setText(room.getDescription());
            roomPriceEdit.setText(Double.toString(room.getPrice()));
            roomImage.setImageBitmap(DbBitmapUtil.getImage(room.getPicture()));
            roomTypeSpinner.setSelection(adapter.getPosition(room.getType()));
        }

        roomDataSource.close();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                try {
                    this.bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    roomImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu_edit, menu);
        if (this.getIntent().getIntExtra(EXTRA_ROOM, 0) == 0) {
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
            deleteRoom();
        }
        if (id == R.id.action_upload) {
            uploadImage();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteRoom() {
        if (room != null) {
            roomDataSource.deleteRoom(room);
        }
        finish();
    }

    public void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }
}


