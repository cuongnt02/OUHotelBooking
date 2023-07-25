package com.example.ouhotelbooking.controllers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.data.model.Hotel;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelHolder> {

    private List<Hotel> hotels;
    private Context context;

    public HotelAdapter(Context context) {
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public void setHotels(List<Hotel> hotelList) {
        hotels = hotelList;
    }

}
