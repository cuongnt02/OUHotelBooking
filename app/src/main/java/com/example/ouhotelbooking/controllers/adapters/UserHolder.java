package com.example.ouhotelbooking.controllers.adapters;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.admin.EditUserActivity;
import com.example.ouhotelbooking.data.model.User;

public class UserHolder extends RecyclerView.ViewHolder  {

    private TextView usernameText;
    private TextView phoneText;
    private TextView userRoleText;
    private User user;


    public UserHolder(@NonNull View itemView) {
        super(itemView);
        usernameText = itemView.findViewById(R.id.user_list_row_username);
        phoneText = itemView.findViewById(R.id.user_list_row_phone);
        userRoleText = itemView.findViewById(R.id.user_list_row_role);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = EditUserActivity.createIntent(view.getContext(), user.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    public void bindUser(User user) {
        this.user = user;
        usernameText.setText(user.getUsername());
        phoneText.setText(user.getPhoneNumber());
        userRoleText.setText(user.getUserRole());
    }

    public User getUser() {
        return this.user;
    }

}
