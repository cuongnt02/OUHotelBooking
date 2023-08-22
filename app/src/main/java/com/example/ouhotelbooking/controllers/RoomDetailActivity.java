package com.example.ouhotelbooking.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.data.datasource.RoomDataSource;
import com.example.ouhotelbooking.data.model.Room;
import com.example.ouhotelbooking.utils.DbBitmapUtil;

import org.w3c.dom.Text;

public class RoomDetailActivity extends AppCompatActivity {
    public static final String EXTRA_ROOM = "com.example.ouhotelbooking.roomdetail";

    private TextView roomDetailTitle;
    private TextView roomDetailDescription;
    private Button pickDateButton;
    private TextView priceText;
    private ImageView roomImage;

    private RoomDataSource roomDataSource;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        roomDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        roomDataSource.close();
    }

    public static Intent createIntent(Context packageContext, int roomId) {
        Intent intent = new Intent(packageContext, RoomDetailActivity.class);
        intent.putExtra(EXTRA_ROOM, roomId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_room_detail);
        roomDataSource = new RoomDataSource(this);
        roomDataSource.open();
        Room room = roomDataSource.getRoom(this.getIntent().getIntExtra(EXTRA_ROOM, 0));
        roomDetailTitle = (TextView) findViewById(R.id.room_detail_type);
        roomDetailDescription = (TextView) findViewById(R.id.room_detail_description);
        pickDateButton = (Button) findViewById(R.id.pick_date_button);
        priceText = findViewById(R.id.price);
        priceText.setText(Double.toString(room.getPrice()) + "VND");
        roomImage = findViewById(R.id.imageView);
        roomImage.setImageBitmap(DbBitmapUtil.getImage(room.getPicture()));
        roomDetailTitle.setText(room.getType());
        roomDetailDescription.setText(room.getDescription());
        pickDateButton.setOnClickListener(btn -> {
            Intent intent = BookingDateActivity.createIntent(this, room.getId());
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
