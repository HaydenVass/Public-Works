package com.example.vasshayden_aidce07;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_aidce07.fragments.FormFragment;

public class MainActivity extends AppCompatActivity implements FormFragment.AddUserToList {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add
                (R.id.formFragmentContainer, FormFragment.newInstance()).commit();
    }

    @Override
    public void addPerson(String firstName, String lastName, int age) {
        Intent returnIntent = new Intent();

        returnIntent.putExtra("com.fullsail.android.jav2ce08.EXTRA_STRING_FIRST_NAME", firstName);
        returnIntent.putExtra("com.fullsail.android.jav2ce08.EXTRA_STRING_LAST_NAME", lastName);
        returnIntent.putExtra("com.fullsail.android.jav2ce08.EXTRA_INT_AGE", age);

        setResult(RESULT_OK, returnIntent);
        finish();
    }

}
