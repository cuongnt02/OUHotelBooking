package com.example.ouhotelbooking.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ouhotelbooking.data.model.Hotel;
import com.example.ouhotelbooking.data.model.Room;
import com.example.ouhotelbooking.data.schema.HotelDb;
import com.example.ouhotelbooking.data.schema.RoomDb;

import java.util.ConcurrentModificationException;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "OUHotelBookingBase.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE_HOTEL = "create table "
            + HotelDb.TABLE_HOTEL + " ("
            + "_id integer primary key autoincrement, "
            + HotelDb.COLUMN_NAME + " varchar(50) , "
            + HotelDb.COLUMN_ADDRESS + " varchar(200)"
            + ");";

    public static final String CREATE_TABLE_ROOM = "create table "
            + RoomDb.TABLE_ROOM + " ("
            + "_id integer primary key autoincrement, "
            + RoomDb.COLUMN_TYPE + " varchar(50), "
            + RoomDb.COLUMN_DESC + " text, "
            + RoomDb.COLUMN_FK_HOTEL + " integer, "
            + "foreign key(" + RoomDb.COLUMN_FK_HOTEL + ")"
            + " references " + HotelDb.TABLE_HOTEL
            +"(" + HotelDb.COLUMN_ID + ")"
            + ");";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_HOTEL);
        sqLiteDatabase.execSQL(CREATE_TABLE_ROOM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
        + newVersion + ", which will destroy all new data.");
        sqLiteDatabase.execSQL("drop table if exists " + HotelDb.TABLE_HOTEL);
        sqLiteDatabase.execSQL("drop table if exists " + RoomDb.TABLE_ROOM);
        onCreate(sqLiteDatabase);
    }
}
