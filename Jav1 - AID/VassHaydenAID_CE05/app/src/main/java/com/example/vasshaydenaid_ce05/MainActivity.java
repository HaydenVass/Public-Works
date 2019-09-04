package com.example.vasshaydenaid_ce05;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vasshaydenaid_ce05.Interfaces.PassPeople;
import com.example.vasshaydenaid_ce05.Objects.Person;
import com.example.vasshaydenaid_ce05.fragments.AdministratorFormFragment;
import com.example.vasshaydenaid_ce05.fragments.InstructorFormFragment;
import com.example.vasshaydenaid_ce05.fragments.NoPeopleFragment;
import com.example.vasshaydenaid_ce05.fragments.PersonList;
import com.example.vasshaydenaid_ce05.fragments.StudentFormFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PassPeople {

    private ArrayList<Person> peopleList;
    private ArrayList<Person> tempPeopleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button refreshBtn = findViewById(R.id.refresh_button);
        Spinner jobSelector = findViewById(R.id.personSpinner);
        TextView noToShow = findViewById(R.id.no_to_show_textfiled);
        peopleList = new ArrayList<>();
        tempPeopleList = new ArrayList<>();

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setUpSpinnerTwo();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.form_frag_container, StudentFormFragment.newInstance()).commit();

            getSupportFragmentManager().beginTransaction().replace(R.id.list_frag_container, NoPeopleFragment.newInstance()).commit();
            refreshBtn.setOnClickListener(refresh);
            noToShow.setVisibility(View.GONE);
            //hide textfield
        }else{
            //hide spinner
            jobSelector.setVisibility(View.GONE);
            refreshBtn.setVisibility(View.GONE);
            //show trxt filed
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
                case "Student":
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.form_frag_container, StudentFormFragment.newInstance()).commit();
                    break;
                case "Instructor":
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.form_frag_container, InstructorFormFragment.newInstance()).commit();
                    break;
                case "Administrator":
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.form_frag_container, AdministratorFormFragment.newInstance()).commit();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    };


    @Override
    public void setNewPerson(Person newPerson) {
        tempPeopleList.add(newPerson);
    }

    private final View.OnClickListener refresh = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            peopleList = new ArrayList<>(tempPeopleList);
            if(peopleList.size() == 0){
                getSupportFragmentManager().beginTransaction().replace(R.id.list_frag_container, NoPeopleFragment.newInstance()).commit();
            }else{
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.list_frag_container, PersonList.newInstance(peopleList)).commit();
            }
        }
    };

}
