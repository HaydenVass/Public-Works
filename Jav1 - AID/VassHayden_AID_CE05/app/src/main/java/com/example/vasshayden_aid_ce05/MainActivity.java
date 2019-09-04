package com.example.vasshayden_aid_ce05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vasshayden_aid_ce05.Objects.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Person> peopleList;
    private Spinner jobSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobSelector = findViewById(R.id.personSpinner);
        if (jobSelector != null) {
            setUpSpinnerTwo();
        }

    }

    private void setUpSpinnerTwo() {
        Spinner sTwo = findViewById(R.id.personSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource
                (this, R.array.spinner_values, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTwo.setAdapter(adapter2);
        sTwo.setOnItemSelectedListener(TwoListener);
    }

    // switch between different adapter views
    private final AdapterView.OnItemSelectedListener TwoListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selection = parent.getItemAtPosition(position).toString();
            switch (selection) {
                case "Array Adapter":

                    break;

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };

}
