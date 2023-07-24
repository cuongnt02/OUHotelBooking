package com.example.ouhotelbooking.utils;

public class RangeSupressException extends Exception {

    public static final String RANGE_SUPRESS_MESSAGE = "The value cannot be used in database query";
    public RangeSupressException() {}

    public RangeSupressException(String message) {
        super(message);
    }
}
