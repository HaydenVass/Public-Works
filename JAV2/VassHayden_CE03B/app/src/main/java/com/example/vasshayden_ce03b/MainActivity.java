//Hayden Vass
//Jav2 1905
//MainActivity
package com.example.vasshayden_ce03b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                ImageFragment.newInstance()).commit();

    }
}
