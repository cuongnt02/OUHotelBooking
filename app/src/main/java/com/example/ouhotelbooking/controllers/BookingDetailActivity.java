package com.example.ouhotelbooking.controllers;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.data.datasource.BookingDataSource;
import com.example.ouhotelbooking.data.datasource.RoomDataSource;
import com.example.ouhotelbooking.data.model.Booking;
import com.example.ouhotelbooking.data.model.Room;
import com.example.ouhotelbooking.utils.DateFormatterUtils;

import org.w3c.dom.Text;

import java.util.Date;

public class BookingDetailActivity extends AppCompatActivity {
    public static final String EXTRA_BOOKING_DATE = "com.example.ouhotelbooking.booking.date";
    public static final String EXTRA_BOOKING_RETURN = "com.example.ouhotelbooking.bookng.return";
    public static final String EXTRA_BOOKING_PRICE = "com.example.ouhotelbooking.booking.price";
    public static final String EXTRA_BOOKING_ROOM_ID = "com.example.ouhotelbooking.booking.room";

    private Booking booking;

    private TextView txtRoomName;
    private TextView txtRoomType;
    private TextView txtRoomPrice;
    private TextView txtBookingPrice;
    private TextView txtBookingCheckIn;
    private TextView txtBookingCheckOut;
    private Button confirmButton;
    private RoomDataSource roomDataSource;
    private BookingDataSource bookingDataSource;

    public static Intent createIntent(Context packageContext, Booking booking) {
        Intent intent = new Intent(packageContext, BookingDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_BOOKING_DATE, booking.getCheckIn());
        bundle.putSerializable(EXTRA_BOOKING_RETURN, booking.getCheckOut());
        bundle.putDouble(EXTRA_BOOKING_PRICE, booking.getTotalPrice());
        bundle.putInt(EXTRA_BOOKING_ROOM_ID, booking.getRoomId());
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        bookingDataSource.open();
        roomDataSource.open();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bookingDataSource.close();
        roomDataSource.close();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booking_detail);
        roomDataSource = new RoomDataSource(this);
        bookingDataSource = new BookingDataSource(this);
        Bundle bundle = getIntent().getExtras();
        int roomId = bundle.getInt(EXTRA_BOOKING_ROOM_ID);
        Date checkIn = (Date) bundle.getSerializable(EXTRA_BOOKING_DATE);
        Date checkOut = (Date) bundle.getSerializable(EXTRA_BOOKING_RETURN);
        double bookingPrice = bundle.getDouble(EXTRA_BOOKING_PRICE);
        roomDataSource.open();
        Room room = roomDataSource.getRoom(roomId);
        roomDataSource.close();

        txtRoomName = findViewById(R.id.layout_booking_detail_room_name);
        txtRoomType = findViewById(R.id.layout_booking_detail_room_type);
        txtRoomPrice = findViewById(R.id.layout_booking_detail_room_price);
        txtBookingPrice = findViewById(R.id.layout_booking_detail_booking_price);
        txtBookingCheckIn = findViewById(R.id.layout_booking_detail_booking_checkin);
        txtBookingCheckOut = findViewById(R.id.layout_booking_detail_booking_checkout);

        txtRoomName.setText(String.format("Tên phòng: %s", room.getTitle()));
        txtRoomType.setText(String.format("Loại phòng: %s", room.getType()));
        txtRoomPrice.setText(String.format("Giá phòng (Theo ngày): %.2f", room.getPrice() ));
        txtBookingPrice.setText(String.format("Giá phòng (Tổng cộng): %.2f", bookingPrice));
        if (checkIn != null) {
            String checkInText = "Ngày nhận phòng (Check In): " + DateFormatterUtils.formatDate(checkIn);
            txtBookingCheckIn.setText(checkInText);
        }
        if (checkOut != null) {
            String checkOutText = "Ngày trả phòng (Check Out): " + DateFormatterUtils.formatDate(checkOut);
            txtBookingCheckOut.setText(checkOutText);
        }

        confirmButton = (Button) findViewById(R.id.layout_booking_detail_confirm);
        confirmButton.setOnClickListener(btn -> {
            bookingDataSource.open();
            booking = new Booking();
            booking.setRoomId(roomId);
            booking.setTotalPrice(bookingPrice);
            booking.setCheckIn(checkIn);
            booking.setCheckOut(checkOut);
            Log.d(TAG, "onCreate: bookingDate: " + booking.getCheckIn());
            bookingDataSource.createBooking(booking);
            bookingDataSource.close();
            Intent intent = new Intent(BookingDetailActivity.this, SearchActivity.class);
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

    @Override
    public void onBackPressed() {
    }
}
