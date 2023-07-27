package com.example.ouhotelbooking.controllers.adapters;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.HotelDetailActivity;
import com.example.ouhotelbooking.data.model.Hotel;

public class HotelHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleTextView;
        private TextView addressTextView;

        private Hotel hotel;

        public HotelHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titleTextView = (TextView) itemView.findViewById(R.id.layout_search_list_item_title);
            addressTextView = (TextView) itemView.findViewById(R.id.layout_search_list_item_address);
        }

        public void bindHotel(Hotel hotel) {
            this.hotel = hotel;
            titleTextView.setText(this.hotel.getName());
            addressTextView.setText(this.hotel.getAddress());
        }

        @Override
        public void onClick(View view) {
            Intent intent = HotelDetailActivity.createIntent(view.getContext(), hotel.getId());
            view.getContext().startActivity(intent);
        }
    }
