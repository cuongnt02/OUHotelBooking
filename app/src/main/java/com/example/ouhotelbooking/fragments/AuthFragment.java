package com.example.ouhotelbooking.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.login.LoginActivity;
import com.example.ouhotelbooking.controllers.register.RegisterActivity;


public class AuthFragment extends Fragment {
    private Button loginButton;
    private Button registerButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_auth, container, false);

        loginButton = view.findViewById(R.id.auth_button_login);
        loginButton.setOnClickListener(btn -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        registerButton = view.findViewById(R.id.auth_button_register);
        registerButton.setOnClickListener(btn -> {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
