package com.example.ouhotelbooking.controllers.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.controllers.MasterActivity;
import com.example.ouhotelbooking.fragments.AuthFragment;
import com.example.ouhotelbooking.fragments.LoginFragment;
import com.example.ouhotelbooking.fragments.RegisterFragment;

public class AuthActivity extends MasterActivity {

    @Override
    protected Fragment createFragment() {
        return new AuthFragment();
    }
}