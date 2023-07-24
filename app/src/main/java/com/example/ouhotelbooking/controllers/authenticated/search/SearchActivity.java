package com.example.ouhotelbooking.controllers.authenticated.search;

import androidx.fragment.app.Fragment;

import com.example.ouhotelbooking.controllers.MasterActivity;
import com.example.ouhotelbooking.fragments.SearchFragment;

public class SearchActivity extends MasterActivity {

    @Override
    protected Fragment createFragment() {
        return new SearchFragment();
    }
}
