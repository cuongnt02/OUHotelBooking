package com.example.ouhotelbooking.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DbBitmapUtil {
    public static byte[] getBytes(Bitmap bitmap) {
        if (bitmap == null) return null;
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Bitmap getImage(byte[] image) {
        if (image != null) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        else return null;
    }
}
