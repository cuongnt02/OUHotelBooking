package com.example.ouhotelbooking.data.datasource;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ouhotelbooking.data.SQLiteHelper;
import com.example.ouhotelbooking.data.model.Booking;
import com.example.ouhotelbooking.data.schema.BookingDb;
import com.example.ouhotelbooking.data.schema.HotelDb;
import com.example.ouhotelbooking.utils.DateFormatterUtils;
import com.example.ouhotelbooking.utils.DbUtils;
import com.example.ouhotelbooking.utils.RangeSupressException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper sqLiteHelper;


    public BookingDataSource(Context context) { sqLiteHelper = new SQLiteHelper(context); }

    public void open() {
        database = sqLiteHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Booking createBooking(Booking booking) {
        ContentValues bookingValues = createBookingValues(booking);

        long insertId = database.insert(BookingDb.TABLE_BOOKING, null,
                bookingValues);

        Booking insertedBooking = getBooking((int) insertId);
        if (insertedBooking == null) throw new RuntimeException("Database insertion failed");
        return insertedBooking;

    }

    public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        DbUtils dbUtils = new DbUtils(this.database);
        try (Cursor cursor = dbUtils.queryAllColumns(BookingDb.TABLE_BOOKING,
                null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Booking booking = cursorToBooking(cursor);
                bookings.add(booking);
                cursor.moveToNext();
            }
        } catch (RangeSupressException e) {
            throw new RuntimeException(e);
        }
        return bookings;
    }

    public Booking getBooking(int bookingId) {
        DbUtils dbUtils = new DbUtils(this.database);
        try (Cursor cursor = dbUtils.queryAllColumns(BookingDb.TABLE_BOOKING,
                BookingDb.COLUMN_ID + "=?",
                new String[]{Integer.toString(bookingId)})) {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) {
                return null;
            }
            return cursorToBooking(cursor);
        } catch (RangeSupressException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBooking(Booking booking) {
        int id = booking.getId();
        ContentValues bookingValues = createBookingValues(booking);
        database.update(BookingDb.TABLE_BOOKING, bookingValues,
                BookingDb.COLUMN_ID + "=?", new String[]{Integer.toString(id)});
    }

    public void deleteBooking(Booking booking) {
        int id = booking.getId();
        database.delete(BookingDb.TABLE_BOOKING, BookingDb.COLUMN_ID + "=?",
                new String[]{Integer.toString(id)});
    }

    private Booking cursorToBooking(Cursor cursor) throws RangeSupressException {
        int id = cursor.getInt(getIndex(cursor, BookingDb.COLUMN_ID));
        String checkIn = cursor.getString(getIndex(cursor, BookingDb.COLUMN_CHECK_IN));
        String checkOut = cursor.getString(getIndex(cursor, BookingDb.COLUMN_CHECK_OUT));
        double totalPrice = cursor.getDouble(getIndex(cursor, BookingDb.COLUMN_TOTAL_PRICE));
        int roomId = cursor.getInt(getIndex(cursor, BookingDb.COLUMN_FK_ROOM));

        Booking booking = new Booking();
        booking.setId(id);
        booking.setCheckIn(DateFormatterUtils.getDate(checkIn));
        booking.setCheckOut(DateFormatterUtils.getDate(checkOut));
        booking.setTotalPrice(totalPrice);
        booking.setRoomId(roomId);
        return booking;
    }

    private int getIndex(Cursor cursor, String columnName) throws RangeSupressException {
        int index = cursor.getColumnIndex(columnName);
        if (index < 0) throw new RangeSupressException(RangeSupressException.RANGE_SUPRESS_MESSAGE);
        return index;
    }

    private ContentValues createBookingValues(Booking booking) {
        ContentValues values = new ContentValues();
        values.put(BookingDb.COLUMN_ID, booking.getId());
        values.put(BookingDb.COLUMN_CHECK_IN, DateFormatterUtils.formatDate(booking.getCheckIn()));
        values.put(BookingDb.COLUMN_CHECK_OUT, DateFormatterUtils.formatDate(booking.getCheckOut()));
        values.put(BookingDb.COLUMN_TOTAL_PRICE, booking.getTotalPrice());
        values.put(BookingDb.COLUMN_FK_ROOM, booking.getRoomId());
        return values;
    }

}
