package com.example.ouhotelbooking.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.ouhotelbooking.R;


public class AuthFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_auth, container, false);
    }

    @Override
    public void onStart() {
        Button btnLogin = requireView().findViewById(R.id.button_login);
        if (btnLogin != null) {
            Log.d("AuthFragment", "onStart: login button valid");
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_auth, new LoginFragment())
                            .commit();
                }
            });
        }

        Button btnRegister = requireView().findViewById(R.id.button_register);
        if (btnRegister != null) {
            Log.d("Auth fragment", "onStart: register button is not null");
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_auth, new RegisterFragment())
                            .commit();
                }
            });

        }
        else {
            Log.d("AuthFragment", "onStart: register button is null!");
        }
        super.onStart();
    }
}
