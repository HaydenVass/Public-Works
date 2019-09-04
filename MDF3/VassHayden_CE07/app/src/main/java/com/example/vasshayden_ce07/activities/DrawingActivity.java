package com.example.vasshayden_ce07.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.vasshayden_ce07.DrawingSurface;
import com.example.vasshayden_ce07.R;
import com.example.vasshayden_ce07.fragments.DrawingFragment;
import com.example.vasshayden_ce07.objects.HiddenTreasure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DrawingActivity extends AppCompatActivity implements DrawingSurface.forResult {

    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        getSupportFragmentManager().beginTransaction().replace(R.id.draw_fragment_container,
                DrawingFragment.newInstance()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_view_list){
            Intent listIntent = new Intent(this, FoundItemsActivity.class);
            listIntent.putExtra("CURRENT_TREASURES", items);
            startActivity(listIntent);
        }
        return true;
    }

    //interface
    @Override
    public void passFoundItems(ArrayList<HiddenTreasure> foundTreasures) {
        ArrayList<String> itemNames = new ArrayList<>();
        ArrayList<String> itemsToSend = new ArrayList<>();
        //get total gold
        int totalGold = 0;
        for (HiddenTreasure ht : foundTreasures) {
            if (ht.getName().equals("Gold")){
                totalGold += ht.getValue();
            }
        }
        //add total gold - add to primary list
        itemsToSend.add(totalGold + " -  Gold" );

        //convert to items to string array
        for (HiddenTreasure ht : foundTreasures) {
            itemNames.add(ht.getName());
        }

        //get unique items  and count
        Set<String> item = new HashSet<>(itemNames);
        for (String key : item) {
            if(!key.equals("Gold")){
                itemsToSend.add(Collections.frequency(itemNames, key) + " - " + key);
            }
        }

        items = itemsToSend;
    }

}
