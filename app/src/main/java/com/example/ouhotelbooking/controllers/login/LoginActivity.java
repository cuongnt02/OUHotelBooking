package com.example.ouhotelbooking.controllers.login;

import androidx.fragment.app.Fragment;

import com.example.ouhotelbooking.controllers.MasterActivity;
import com.example.ouhotelbooking.fragments.LoginFragment;

public class LoginActivity extends MasterActivity {
    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }

}
