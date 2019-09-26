package com.example.vasshayden_aidce08.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.vasshayden_aidce08.R;
import com.example.vasshayden_aidce08.fragments.FormFragment;
import com.example.vasshayden_aidce08.objects.Monster;

public class FormActivity extends AppCompatActivity implements FormFragment.MonsterCreatedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportFragmentManager().beginTransaction().add
                (R.id.formFragmentContainer, FormFragment.newInstance()).commit();
    }

    @Override
    public void monsterCreated(Monster monster) {
        Intent returnMonsterIntent = new Intent();
        returnMonsterIntent.putExtra("EXTRA_MONSTER", monster);
        setResult(RESULT_OK, returnMonsterIntent);
        finish();
    }
}
