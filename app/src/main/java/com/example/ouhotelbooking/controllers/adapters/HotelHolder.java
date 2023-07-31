package com.example.ouhotelbooking.controllers.adapters;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.HotelDetailActivity;
import com.example.ouhotelbooking.data.model.Hotel;

public class HotelHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView addressTextView;

        private Hotel hotel;
        private View view;

        public HotelHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.layout_search_list_item_title);
            addressTextView = (TextView) itemView.findViewById(R.id.layout_search_list_item_address);
            this.view = itemView;
        }

        public void bindHotel(Hotel hotel) {
            this.hotel = hotel;
            titleTextView.setText(this.hotel.getName());
            addressTextView.setText(this.hotel.getAddress());
        }

        public Hotel getHotel() {
            return this.hotel;
        }

        public View getView() {
            return this.view;
        }
    }
