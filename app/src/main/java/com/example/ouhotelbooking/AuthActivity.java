package com.example.ouhotelbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ouhotelbooking.fragments.AuthFragment;
import com.example.ouhotelbooking.fragments.LoginFragment;
import com.example.ouhotelbooking.fragments.RegisterFragment;

public class AuthActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container_auth);
        if (fragment == null) {
            fragment = new AuthFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container_auth, fragment)
                    .commit();
        }

    }

}