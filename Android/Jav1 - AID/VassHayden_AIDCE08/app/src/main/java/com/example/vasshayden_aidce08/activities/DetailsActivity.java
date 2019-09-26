package com.example.vasshayden_aidce08.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_aidce08.R;
import com.example.vasshayden_aidce08.fragments.DetailsFragment;
import com.example.vasshayden_aidce08.objects.Monster;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.DeleteMonster {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportFragmentManager().beginTransaction().add
                (R.id.detailsFragmentContainer, DetailsFragment.newInstance()).commit();
    }

    @Override
    public void removeMonster(Monster monster) {
        Intent returnMonsterToDelete = new Intent();
        returnMonsterToDelete.putExtra("DELETE_MONSTER", monster);
        setResult(RESULT_OK, returnMonsterToDelete);
        finish();
    }

    @Override
    public void shareMonster(Monster monster) {
        //crteate a new Implicit intent to open "something"
        Intent implicitIntent = new Intent(Intent.ACTION_SEND);
        // attach data
        implicitIntent.putExtra("NAME", monster.getName());
        implicitIntent.putExtra("LEGS", monster.getNumberOfLegs());
        implicitIntent.putExtra("FUZ", monster.getHasFurStr());
        implicitIntent.setType("text/plain");
        // start intent
        startActivity(implicitIntent);
    }
}
