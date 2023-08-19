package com.example.ouhotelbooking.data.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ouhotelbooking.data.SQLiteHelper;
import com.example.ouhotelbooking.data.model.User;
import com.example.ouhotelbooking.data.schema.UserDb;
import com.example.ouhotelbooking.utils.DbUtils;
import com.example.ouhotelbooking.utils.RangeSupressException;

import java.util.ArrayList;
import java.util.List;

public class UserDataSource {

    private SQLiteDatabase database;
    private SQLiteHelper sqLiteHelper;

    public UserDataSource(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    public void open() {
        database = sqLiteHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public User createUser(User user) {
        if (getUser(user.getUsername()) != null) return null;
        ContentValues userValues = createUserValues(user);

        long insertId = database.insert(UserDb.TABLE_USER, null, userValues);

        return getUser((int) insertId);
    }

    public User getUser(int userId) {
        DbUtils dbUtils = new DbUtils(this.database);
        try (Cursor cursor = dbUtils.queryAllColumns(UserDb.TABLE_USER,
                UserDb.COLUMN_ID + "=?",
                new String[]{String.valueOf(userId)})) {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) return null;
            return cursorToUser(cursor);
        } catch (RangeSupressException e) {
            throw new RuntimeException(e);
        }

    }

    public User getUser(String username) {
        DbUtils dbUtils = new DbUtils(this.database);
        try (Cursor cursor = dbUtils.queryAllColumns(UserDb.TABLE_USER,
                UserDb.COLUMN_USERNAME + " like ?",
                new String[]{username})) {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) return null;
            return cursorToUser(cursor);
        } catch (RangeSupressException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        DbUtils dbUtils = new DbUtils(database);
        try (Cursor cursor = dbUtils.queryAllColumns(UserDb.TABLE_USER, null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                users.add(cursorToUser(cursor));
                cursor.moveToNext();
            }
        } catch (RangeSupressException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void updateUser(User user) {
        ContentValues userValues = createUserValues(user);
        database.update(UserDb.TABLE_USER, userValues, UserDb.COLUMN_ID + "=?",
                new String[]{String.valueOf(user.getId())});
    }

    public void deleteUser(User user) {
        database.delete(UserDb.TABLE_USER, UserDb.COLUMN_ID + "=?",
                new String[]{String.valueOf(user.getId())});
    }

    private User cursorToUser(Cursor cursor) throws RangeSupressException {
        int userId = cursor.getInt(getIndex(cursor, UserDb.COLUMN_ID));
        String userName = cursor.getString(getIndex(cursor, UserDb.COLUMN_USERNAME));
        String password = cursor.getString(getIndex(cursor, UserDb.COLUMN_PASSWORD));
        String name = cursor.getString(getIndex(cursor, UserDb.COLUMN_NAME));
        String phoneNumber = cursor.getString(getIndex(cursor, UserDb.COLUMN_PHONE_NUMBER));
        String email = cursor.getString(getIndex(cursor, UserDb.COLUMN_EMAIL));
        String userRole = cursor.getString(getIndex(cursor, UserDb.COLUMN_USER_ROLE));

        User user = new User();
        user.setId(userId);
        user.setUsername(userName);
        user.setPassword(password);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setUserRole(userRole);
        return user;
    }

    private int getIndex(Cursor cursor, String columnName) throws RangeSupressException {
        int index = cursor.getColumnIndex(columnName);
        if (index < 0) {
            throw new RangeSupressException(RangeSupressException.RANGE_SUPRESS_MESSAGE);
        }
        return index;
    }

    private ContentValues createUserValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDb.COLUMN_USERNAME, user.getUsername());
        contentValues.put(UserDb.COLUMN_PASSWORD, user.getPassword());
        contentValues.put(UserDb.COLUMN_NAME, user.getName());
        contentValues.put(UserDb.COLUMN_EMAIL, user.getEmail());
        contentValues.put(UserDb.COLUMN_USER_ROLE, user.getUserRole());
        contentValues.put(UserDb.COLUMN_PHONE_NUMBER, user.getPhoneNumber());
        return contentValues;
    }
}
