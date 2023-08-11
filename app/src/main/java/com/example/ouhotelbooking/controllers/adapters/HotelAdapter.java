package com.example.ouhotelbooking.controllers.adapters;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.HotelDetailActivity;
import com.example.ouhotelbooking.controllers.admin.EditHotelActivity;
import com.example.ouhotelbooking.data.model.Hotel;

import java.util.Arrays;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelHolder> {
    private List<Hotel> hotels;
    private Context context;
    private boolean admin = false;

    public HotelAdapter(Context context) {
        this.context = context;
    }

    public HotelAdapter(Context context, boolean admin) {
        this.context = context;
        this.admin = admin;
    }

    @NonNull
    @Override
    public HotelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_search_list_row, parent, false);
        return new HotelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelHolder holder, int position) {
        Hotel hotel = hotels.get(position);
        holder.bindHotel(hotel);
        holder.getView().setOnClickListener(v -> {
            Intent intent;
            if (admin) {
                intent = EditHotelActivity.createIntent(this.context, hotel.getId());
            }
            else {
                intent = HotelDetailActivity.createIntent(this.context, hotel.getId());
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public void setHotels(List<Hotel> hotelList) {
        hotels = hotelList;
    }

}
