package com.example.vasshayden_aidce08;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.vasshayden_aidce08.activities.DetailsActivity;
import com.example.vasshayden_aidce08.activities.FormActivity;
import com.example.vasshayden_aidce08.fragments.MainListFragment;
import com.example.vasshayden_aidce08.objects.Monster;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainListFragment.SelectedMonster{
    private static final String TAG = "today";

    private ArrayList<Monster> monsters;
    private Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        monsters = new ArrayList<>();
        c = this;

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainFragmentContainer, MainListFragment.newInstance(monsters)).commit();



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logic for fab
                Intent formIntent = new Intent(c, FormActivity.class);
                startActivityForResult(formIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Monster monsterToAdd = (Monster) Objects.requireNonNull(data).getSerializableExtra("EXTRA_MONSTER");
                monsters.add(monsterToAdd);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFragmentContainer, MainListFragment.newInstance(monsters)).commit();
            }
        }else{
            //if result code is 2 - to delete a monster
            if(resultCode == RESULT_OK){
                Monster monsterToRemove = (Monster) Objects.requireNonNull(data).getSerializableExtra("DELETE_MONSTER");
                for (Monster listMonster : monsters) {
                    
                    if(listMonster.equals(monsterToRemove)){
                        Log.i(TAG, "onActivityResult: same same");
                        monsters.remove(listMonster);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.mainFragmentContainer, MainListFragment.newInstance(monsters)).commit();
                    }
                    Snackbar.make(findViewById(R.id.mainFragmentContainer), "Monster Deleted", Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void itemSelectedMonster(Monster monster) {
        // when a monster is selected from list view
        Intent detailsIntent = new Intent(c, DetailsActivity.class);
        detailsIntent.putExtra("SELECTED_MONSTER", monster);
        startActivityForResult(detailsIntent, 2);
    }

}
