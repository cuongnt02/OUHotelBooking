package com.example.ouhotelbooking.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.authenticated.search.SearchActivity;

public class LoginFragment  extends Fragment {
    private Button loginButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_login, container, false);
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(btn -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
