package com.example.ouhotelbooking.data.model;

import androidx.annotation.NonNull;

public class Hotel {
    private int id;
    private String name;
    private String address;


    public Hotel() {
    }

    public Hotel(String name, String address) {
        this.name = name;
        this.address = address;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

