package com.example.ouhotelbooking.controllers.adapters;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.RoomDetailActivity;
import com.example.ouhotelbooking.data.model.Room;

import org.w3c.dom.Text;

public class RoomHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView typeTextView;
    private TextView descriptionTextView;

    private Room room;

    public RoomHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        typeTextView = (TextView) itemView.findViewById(R.id.layout_room_list_item_type);
        descriptionTextView = (TextView) itemView.findViewById(R.id.layout_room_list_item_description);
    }

    public void bindRoom(Room room) {
        this.room = room;
        typeTextView.setText(this.room.getType());
        descriptionTextView.setText(this.room.getDescription());
    }

    @Override
    public void onClick(View view) {
        Intent intent = RoomDetailActivity.createIntent(view.getContext(), room.getId());
        view.getContext().startActivity(intent);
    }
}
