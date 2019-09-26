package com.example.vasshayden_ce07.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_ce07.R;
import com.example.vasshayden_ce07.fragments.CreditsFragment;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        getSupportFragmentManager().beginTransaction().replace(R.id.credits_frag_container,
                CreditsFragment.newInstance()).commit();
    }
}
