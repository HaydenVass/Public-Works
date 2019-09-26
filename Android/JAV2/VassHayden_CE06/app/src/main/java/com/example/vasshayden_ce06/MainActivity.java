package com.example.vasshayden_ce06;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_ce06.fragments.MainListFragments;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                MainListFragments.newInstance()).commit();
    }
}
