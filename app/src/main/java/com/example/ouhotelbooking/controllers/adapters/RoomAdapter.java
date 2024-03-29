package com.example.ouhotelbooking.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.RoomChoiceActivity;
import com.example.ouhotelbooking.controllers.RoomDetailActivity;
import com.example.ouhotelbooking.controllers.admin.EditRoomActivity;
import com.example.ouhotelbooking.data.model.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomHolder> {

    private List<Room> rooms;
    private Context context;
    private boolean admin = false;

    public RoomAdapter(Context context) {this.context = context;}

    public RoomAdapter(Context context, boolean admin) {
        this.context = context;
        this.admin = admin;
    }

    @NonNull
    @Override
    public RoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_room_list_row, parent, false);
        return new RoomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomHolder holder, int position) {
        Room room = rooms.get(position);
        holder.bindRoom(room);
        holder.getView().setOnClickListener(view -> {
            Intent intent;
            if (!admin) {
                intent = RoomDetailActivity.createIntent(this.context, room.getId());
            } else {
                intent = EditRoomActivity.createIntent(this.context, room.getId(), room.getHotelId());
            }
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }


    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
