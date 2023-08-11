package com.example.ouhotelbooking.controllers.adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.data.model.Room;
import com.example.ouhotelbooking.utils.DbBitmapUtil;

import java.util.Locale;

public class RoomHolder extends RecyclerView.ViewHolder {

    private TextView roomTypeText;
    private TextView roomTitleText;
    private TextView roomDescriptionText;

    private TextView roomPrice;

    private ImageView roomImage;


    private Room room;
    private View view;

    public RoomHolder(@NonNull View itemView) {
        super(itemView);
        roomTypeText = (TextView) itemView.findViewById(R.id.layout_room_list_item_type);
        roomTitleText = (TextView) itemView.findViewById(R.id.layout_room_list_item_title);
        roomDescriptionText = (TextView) itemView.findViewById(R.id.layout_room_list_item_description);
        roomImage = (ImageView) itemView.findViewById(R.id.layout_room_list_item_image);
        roomPrice = (TextView) itemView.findViewById(R.id.layout_room_list_item_price);
        this.view = itemView;
    }

    public void bindRoom(Room room) {
        this.room = room;
        roomTypeText.setText(this.room.getType());
        roomDescriptionText.setText(this.room.getDescription());
        roomTitleText.setText(this.room.getTitle());
        roomPrice.setText(String.format(Locale.US, "%.2f", room.getPrice()));
        roomImage.setImageBitmap(DbBitmapUtil.getImage(room.getPicture()));

    }


    public Room getRoom() { return this.room; }
    public View getView() { return this.view; }
}
