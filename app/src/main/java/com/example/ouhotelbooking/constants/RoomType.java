package com.example.ouhotelbooking.constants;

public enum RoomType {
    SINGLE,
    DOUBLE,
    DELUXE;

    public final String toString() {
        return switch(this) {
            case SINGLE -> "Phòng đơn";
            case DOUBLE -> "Phòng đôi";
            case DELUXE -> "Phòng đặc biệt";
        };
    }

}
