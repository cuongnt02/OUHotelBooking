package com.example.ouhotelbooking.constants;

public enum UserRole {
    USER,
    ADMIN;

    public final String toString() {
        return switch (this) {
            case USER -> "User";
            case ADMIN -> "Admin";
        };
    }
}
