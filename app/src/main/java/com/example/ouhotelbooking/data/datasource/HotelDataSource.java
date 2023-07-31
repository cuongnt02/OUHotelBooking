package com.example.ouhotelbooking.data.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ouhotelbooking.data.SQLiteHelper;
import com.example.ouhotelbooking.data.model.Hotel;
import com.example.ouhotelbooking.data.schema.HotelDb;
import com.example.ouhotelbooking.utils.DbUtils;
import com.example.ouhotelbooking.utils.RangeSupressException;

import java.util.ArrayList;
import java.util.List;

public class HotelDataSource {

    private SQLiteDatabase database;
    private SQLiteHelper sqLiteHelper;

    public HotelDataSource(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = sqLiteHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void deleteHotel(Hotel hotel) {
        int id = hotel.getId();
        database.delete(HotelDb.TABLE_HOTEL,
                HotelDb.COLUMN_ID + "=?",
                new String[] {Integer.toString(id)});
    }

    public void updateHotel(Hotel hotel) {
        int id = hotel.getId();
        ContentValues contentValues = createHotelValues(hotel);
        database.update(HotelDb.TABLE_HOTEL,
                contentValues,
                HotelDb.COLUMN_ID + "=?",
                new String[]{Integer.toString(id)});
    }

    public Hotel createHotel(Hotel hotel) {
        ContentValues hotelProperties = createHotelValues(hotel);

        long insertId = database.insert(HotelDb.TABLE_HOTEL, null, hotelProperties);

        Hotel insertedHotel = getHotel((int) insertId);
        if (insertedHotel == null) throw new RuntimeException("Database insertion failed");
        return insertedHotel;
    }

    public Hotel getHotel(int hotelId) {
        DbUtils dbUtils = new DbUtils(this.database);
        try(Cursor cursor = dbUtils.queryAllColumns(HotelDb.TABLE_HOTEL,
                HotelDb.COLUMN_ID+"=?",
                new String[]{Integer.toString(hotelId)})) {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) {
                return null;
            }
            return cursorToHotel(cursor);

        } catch (RangeSupressException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Hotel> getHotels() {
        List<Hotel> hotels = new ArrayList<>();
        DbUtils dbUtils = new DbUtils(this.database);
        try (Cursor cursor = dbUtils.queryAllColumns(HotelDb.TABLE_HOTEL, null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Hotel hotel = cursorToHotel(cursor);
                hotels.add(hotel);
                cursor.moveToNext();
            }
        } catch (RangeSupressException e) {
            throw new RuntimeException(e);
        }
        return hotels;
    }

    private Hotel cursorToHotel(Cursor cursor) throws RangeSupressException {
        int id = cursor.getInt(getIndex(cursor, HotelDb.COLUMN_ID));
        String name = cursor.getString(getIndex(cursor, HotelDb.COLUMN_NAME));
        String address = cursor.getString(getIndex(cursor, HotelDb.COLUMN_ADDRESS));

        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setName(name);
        hotel.setAddress(address);
        return hotel;
    }

    private int getIndex(Cursor cursor, String columnName) throws RangeSupressException {
        int index = cursor.getColumnIndex(columnName);
        if (index < 0) {
            throw new RangeSupressException(RangeSupressException.RANGE_SUPRESS_MESSAGE);
        }
        return index;
    }

    private ContentValues createHotelValues(Hotel hotel) {
        ContentValues values = new ContentValues();
        values.put(HotelDb.COLUMN_NAME, hotel.getName());
        values.put(HotelDb.COLUMN_ADDRESS, hotel.getAddress());
        return values;
    }


}
