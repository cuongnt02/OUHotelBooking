package com.example.ouhotelbooking.data.model;

import androidx.recyclerview.widget.RecyclerView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Room {
    private int id;
    private String type;
    private String description;
    private int hotelId;

    public Room () {}

    public Room(String type, String description, int hotelId) {
        this.type = type;
        this.description = description;
        this.hotelId = hotelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
