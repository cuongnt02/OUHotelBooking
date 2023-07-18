package com.example.ouhotelbooking.controllers.register;

import androidx.fragment.app.Fragment;

import com.example.ouhotelbooking.controllers.MasterActivity;
import com.example.ouhotelbooking.fragments.RegisterFragment;

public class RegisterActivity extends MasterActivity {
    @Override
    protected Fragment createFragment() {
        return new RegisterFragment();
    }
}
