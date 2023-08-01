package com.example.ouhotelbooking.controllers.adapters;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.HotelDetailActivity;
import com.example.ouhotelbooking.data.model.Hotel;
import com.example.ouhotelbooking.utils.DbBitmapUtil;

import java.util.Arrays;

public class HotelHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView addressTextView;
        private ImageView imageView;

        private Hotel hotel;
        private View view;

        public HotelHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.layout_search_list_item_title);
            addressTextView = (TextView) itemView.findViewById(R.id.layout_search_list_item_address);
            imageView = (ImageView) itemView.findViewById(R.id.hotel_row_image);
            this.view = itemView;
        }

        public void bindHotel(Hotel hotel) {
            this.hotel = hotel;
            titleTextView.setText(this.hotel.getName());
            addressTextView.setText(this.hotel.getDescription());
            Log.d(TAG, "bindHotel: " + Arrays.toString(hotel.getPicture()));
            imageView.setImageBitmap(DbBitmapUtil.getImage(this.hotel.getPicture()));
        }

        public Hotel getHotel() {
            return this.hotel;
        }

        public View getView() {
            return this.view;
        }
    }
