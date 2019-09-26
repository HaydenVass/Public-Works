package com.example.vasshayden_ce03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_ce03.fragments.ForecastFragment;

public class ForecastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);


        ForecastFragment fragment = ForecastFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.forecast_frag_container, fragment, ForecastFragment.TAG)
                .commit();

    }
}
