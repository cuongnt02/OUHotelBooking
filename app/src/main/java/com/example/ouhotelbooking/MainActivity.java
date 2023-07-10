package com.example.ouhotelbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ouhotelbooking.R;
import com.example.ouhotelbooking.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Hello
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Button btnLogin = findViewById(R.id.button);
        btnLogin.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
                if (fragment == null) {
                    fragment = new LoginFragment();
                    fragmentManager.beginTransaction()
                            .add(R.id.fragment_container, fragment)
                            .commit();
                }
            }
        });


    }
}