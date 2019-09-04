package com.example.vasshayden_ce07.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_ce07.R;
import com.example.vasshayden_ce07.fragments.FoundTreasureListFragment;

import java.util.ArrayList;

public class FoundItemsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_items);
        //get array of found items - pass it into the fragment
        ArrayList<String> foundItems = getIntent().getStringArrayListExtra("CURRENT_TREASURES");
        getSupportFragmentManager().beginTransaction().replace(R.id.found_items_frag_container,
                FoundTreasureListFragment.newInstance(foundItems)).commit();
    }
}
