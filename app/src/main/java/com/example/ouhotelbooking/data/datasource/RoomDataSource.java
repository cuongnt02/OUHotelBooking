package com.example.ouhotelbooking.data.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ouhotelbooking.data.SQLiteHelper;
import com.example.ouhotelbooking.data.model.Hotel;
import com.example.ouhotelbooking.data.model.Room;
import com.example.ouhotelbooking.data.schema.RoomDb;
import com.example.ouhotelbooking.utils.DbUtils;
import com.example.ouhotelbooking.utils.RangeSupressException;

import java.util.ArrayList;
import java.util.List;

public class RoomDataSource {

    private SQLiteDatabase database;

    private SQLiteHelper sqLiteHelper;

    public RoomDataSource(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    public void open() {
        database = sqLiteHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Room createRoom(Room room) {
        ContentValues values = createRoomValues(room);

        long insertId = database.insert(RoomDb.TABLE_ROOM, null, values);

        return getRoom((int) insertId);
    }

    public Room getRoom(int roomId) {
        DbUtils dbUtils = new DbUtils(this.database);
        try (Cursor cursor = dbUtils.queryAllColumns(RoomDb.TABLE_ROOM,
                RoomDb.COLUMN_ID+"=?",
                new String[]{Integer.toString(roomId)})) {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) {
                return null;
            }
            return cursorToRoom(cursor);
        } catch (RangeSupressException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        DbUtils dbUtils = new DbUtils(this.database);
        try (Cursor cursor = dbUtils.queryAllColumns(RoomDb.TABLE_ROOM, null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Room room = cursorToRoom(cursor);
                rooms.add(room);
                cursor.moveToNext();
            }
        } catch (RangeSupressException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    private Room cursorToRoom(Cursor cursor) throws RangeSupressException {
        int id = cursor.getInt(getIndex(cursor, RoomDb.COLUMN_ID));
        String type = cursor.getString(getIndex(cursor, RoomDb.COLUMN_TYPE));
        String description = cursor.getString(getIndex(cursor, RoomDb.COLUMN_DESC));
        int hotelId = cursor.getInt(getIndex(cursor, RoomDb.COLUMN_FK_HOTEL));

        Room room = new Room();
        room.setId(id);
        room.setType(type);
        room.setDescription(description);
        room.setHotelId(hotelId);
        return room;
    }

    private int getIndex(Cursor cursor, String columnName) throws RangeSupressException {
        int index = cursor.getColumnIndex(columnName);
        if (index < 0) {
            throw new RangeSupressException(RangeSupressException.RANGE_SUPRESS_MESSAGE);
        }
        return index;
    }

    private ContentValues createRoomValues(Room room) {
        ContentValues values = new ContentValues();
        values.put(RoomDb.COLUMN_TYPE, room.getType());
        values.put(RoomDb.COLUMN_DESC, room.getDescription());
        values.put(RoomDb.COLUMN_FK_HOTEL, room.getHotelId());
        return values;
    }
}
