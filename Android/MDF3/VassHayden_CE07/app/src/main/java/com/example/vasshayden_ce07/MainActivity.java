package com.example.vasshayden_ce07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_ce07.fragments.MainMenuFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            MainMenuFragment fragment = MainMenuFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, MainMenuFragment.TAG)
                    .commit();
        }
    }

}
