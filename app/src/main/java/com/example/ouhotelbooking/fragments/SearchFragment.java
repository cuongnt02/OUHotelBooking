package com.example.ouhotelbooking.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.data.datasource.HotelDataSource;
import com.example.ouhotelbooking.data.model.Hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchFragment extends Fragment {

    private HotelDataSource hotelDataSource;

    private ListView listView;

    @Override
    public void onResume() {
        hotelDataSource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        hotelDataSource.close();
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        hotelDataSource = new HotelDataSource(getActivity());
        hotelDataSource.open();
        hotelDataSource.createHotel(new Hotel("Rose Hotel", "VUNG TAU"));
        hotelDataSource.createHotel(new Hotel("Rose Hotel", "VUNG TAU"));
        hotelDataSource.createHotel(new Hotel("Rose Hotel", "VUNG TAU"));
        hotelDataSource.createHotel(new Hotel("Rose Hotel", "VUNG TAU"));
        List<Hotel> hotels = hotelDataSource.getHotels();
        View v = inflater.inflate(R.layout.layout_search, container, false);
        listView = (ListView) v.findViewById(R.id.list_hotels);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, hotels.stream().map(Hotel::toString).collect(Collectors.toList()));
        listView.setAdapter(adapter);
        return v;
    }
}
