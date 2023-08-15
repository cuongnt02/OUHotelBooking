package com.example.ouhotelbooking.controllers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.data.datasource.BookingDataSource;
import com.example.ouhotelbooking.data.datasource.RoomDataSource;
import com.example.ouhotelbooking.data.model.Booking;
import com.example.ouhotelbooking.data.model.Room;
import com.example.ouhotelbooking.dialogs.DatePicker;
import com.example.ouhotelbooking.utils.DateFormatterUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookingDateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String EXTRA_ROOM = "com.example.ouhotelbooking.booking.room";
    private RoomDataSource roomDataSource;
    private Spinner daysSpinner;
    private Button confirmDateButton;
    private Button confirmButton;

    private Date date;


    @Override
    protected void onResume() {
        super.onResume();
        roomDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        roomDataSource.close();
    }

    public static Intent createIntent(Context packageContext, int roomId) {
        Intent intent = new Intent(packageContext, BookingDateActivity.class);
        intent.putExtra(EXTRA_ROOM, roomId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booking_pick_date);
        roomDataSource = new RoomDataSource(this);
        roomDataSource.open();
        Room room = roomDataSource.getRoom(this.getIntent().getIntExtra(EXTRA_ROOM, 0));
        confirmDateButton = (Button) findViewById(R.id.layout_booking_confirm_date);
        confirmDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = new DatePicker();
                datePicker.show(getSupportFragmentManager(), "DATE PICK");
            }
        });
        daysSpinner = (Spinner) findViewById(R.id.layout_booking_days);
        ArrayAdapter<Integer> days = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item,
                IntStream.range(1, 7).boxed().collect(Collectors.toList()));
        daysSpinner.setAdapter(days);
        confirmButton = (Button) findViewById(R.id.layout_booking_confirm);
        confirmButton.setOnClickListener( btn -> {
                Date bookingDate = date;
                Calendar cal = Calendar.getInstance();
                cal.setTime(bookingDate);
                int daysBooked = (int) daysSpinner.getSelectedItem();
                cal.add(Calendar.DATE, daysBooked);
                Date checkOut = cal.getTime();
                double totalPrice = room.getPrice() * daysBooked;

                Booking booking = new Booking();
                booking.setRoomId(room.getId());
                booking.setTotalPrice(totalPrice);
                booking.setCheckIn(bookingDate);
                booking.setCheckOut(checkOut);
                Intent intent = BookingDetailActivity.createIntent(this, booking);
                startActivity(intent);
        });
        roomDataSource.close();
    }


    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date = calendar.getTime();
        confirmDateButton.setText(selectedDate);
    }
}
