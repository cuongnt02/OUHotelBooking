package com.example.ouhotelbooking.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ouhotelbooking.constants.UserRole;
import com.example.ouhotelbooking.data.model.Hotel;
import com.example.ouhotelbooking.data.model.Room;
import com.example.ouhotelbooking.data.model.User;
import com.example.ouhotelbooking.data.schema.BookingDb;
import com.example.ouhotelbooking.data.schema.HotelDb;
import com.example.ouhotelbooking.data.schema.RoomDb;
import com.example.ouhotelbooking.data.schema.UserDb;

import java.util.ConcurrentModificationException;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "OUHotelBookingBase.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE_HOTEL = "create table "
            + HotelDb.TABLE_HOTEL + " ("
            + "_id integer primary key autoincrement, "
            + HotelDb.COLUMN_NAME + " varchar(50),"
            + HotelDb.COLUMN_DESCRIPTION + " text,"
            + HotelDb.COLUMN_PICTURE + " longblob"
            + ");";

    public static final String CREATE_TABLE_ROOM = "create table "
            + RoomDb.TABLE_ROOM + " ("
            + "_id integer primary key autoincrement, "
            + RoomDb.COLUMN_TITLE + " varchar(100),"
            + RoomDb.COLUMN_TYPE + " varchar(50), "
            + RoomDb.COLUMN_DESC + " text, "
            + RoomDb.COLUMN_FK_HOTEL + " integer, "
            + RoomDb.COLUMN_PRICE + " double, "
            + RoomDb.COLUMN_PICTURE + " longblob,"
            + "foreign key(" + RoomDb.COLUMN_FK_HOTEL + ")"
            + " references " + HotelDb.TABLE_HOTEL
            +"(" + HotelDb.COLUMN_ID + ") on delete cascade"
            + ");";

    public static final String CREATE_TABLE_BOOKING = "create table "
            + BookingDb.TABLE_BOOKING + " ("
            + "_id integer primary key autoincrement, "
            + BookingDb.COLUMN_CHECK_IN + " text, "
            + BookingDb.COLUMN_CHECK_OUT + " text, "
            + BookingDb.COLUMN_TOTAL_PRICE + " double, "
            + BookingDb.COLUMN_FK_ROOM + " integer,"
            + "foreign key(" + BookingDb.COLUMN_FK_ROOM + ")"
            + " references " + RoomDb.TABLE_ROOM
            + "(" + RoomDb.COLUMN_ID + ") on delete cascade"
            + ");";

    public static final String CREATE_TABLE_USER = "create table "
            + UserDb.TABLE_USER + " ("
            + "_id integer primary key autoincrement, "
            + UserDb.COLUMN_USERNAME + " text, "
            + UserDb.COLUMN_PASSWORD + " text, "
            + UserDb.COLUMN_NAME + " text, "
            + UserDb.COLUMN_PHONE_NUMBER + " text, "
            + UserDb.COLUMN_EMAIL + " text, "
            + UserDb.COLUMN_USER_ROLE + " text "
            + ");";

    public static final String INSERT_ADMIN_ACCOUNT = "insert into "
            + UserDb.TABLE_USER + "(" + UserDb.COLUMN_USERNAME + ", "
            + UserDb.COLUMN_PASSWORD + ", "
            + UserDb.COLUMN_USER_ROLE + ") "
            + "values('admin', 'admin', '" + UserRole.ADMIN.toString()+ "');";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_HOTEL);
        sqLiteDatabase.execSQL(CREATE_TABLE_ROOM);
        sqLiteDatabase.execSQL(CREATE_TABLE_BOOKING);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(INSERT_ADMIN_ACCOUNT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
        + newVersion + ", which will destroy all new data.");
        sqLiteDatabase.execSQL("drop table if exists " + HotelDb.TABLE_HOTEL);
        sqLiteDatabase.execSQL("drop table if exists " + RoomDb.TABLE_ROOM);
        sqLiteDatabase.execSQL("drop table if exists " + BookingDb.TABLE_BOOKING);
        sqLiteDatabase.execSQL("drop table if exists " + UserDb.TABLE_USER);
        onCreate(sqLiteDatabase);
    }
}
