package com.example.vasshayden_ce05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Person> peopleList;
    private ListView mListView;
    private ImageView personImg;
    private TextView selectedPersonName;
    private TextView selectedPersonId;
    private TextView selectedPersonDate;
    private TextView selectedPersonDescription;

    private static final String TAG = "JTODAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findListView();
        peopleList = new ArrayList<>();
        createPeopleList();
        personImg = findViewById(R.id.personImg);
        selectedPersonName = findViewById(R.id.nameLabel);
        selectedPersonDate = findViewById(R.id.dateLabel);
        selectedPersonId = findViewById(R.id.idLabel);
        selectedPersonDescription = findViewById(R.id.descriptionText);
        setTitles(0);
        if (mListView != null){
            findListView();
            setUpBaseAdapter();
            // click listener for list view
            mListView.setOnItemClickListener(setText);
        }else{
            setUpSpinnerOne();
        }

    }
    // assigns list view
    private void findListView() {
        try {
            mListView = findViewById(R.id.cell);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "findListView: test");
        }
    }

    private final AdapterView.OnItemClickListener setText = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setTitles(position);
        }
    };

    // assigns custom adapter to list and grid views
    private void setUpBaseAdapter(){
        if(mListView != null){
            PersonAdapter pa = new PersonAdapter(this, peopleList);
            mListView.setAdapter(pa);
        }
    }

    //creates data to populate list / grid view
    private void createPeopleList(){
        String description = "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut " +
                "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu";
        int[] id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String[] firstNames = {"Margo", "Robbie", "Joe", "John", "Paul", "Ringo", "George", "Ben", "Lindsey", "Chloe"};
        String[] lastNames = {"Smith", "Robs", "Slim","Lennon", "Rick", "Star", "Tod", "Nicholes", "Kay", "Cook"};
        int[] drawables = {R.drawable.girl4, R.drawable.boy, R.drawable.bo1, R.drawable.boy3, R.drawable.girl, R.drawable.girl2,
                R.drawable.girl3, R.drawable.man, R.drawable.man2, R.drawable.man3};
        String [] birthday = {"3/12/1992", "4/6/1930", "8/4/1993", "12/3/1990", "9/22/1984", "7/11/1992"
                ,"5/17/1989", "8/28/1980", "9/4/2000", "1/15/1990"};
        for (int i = 0; i < 10; i++) {
            Person personnToAdd = new Person(firstNames[i], lastNames[i], id[i], birthday[i], drawables[i], description);
            peopleList.add(personnToAdd);
        }
    }

    // sets up spinner for landscape
    private void setUpSpinnerOne(){
        Spinner spinnerOne = findViewById(R.id.spinner1);
        ArrayAdapter<Person> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, peopleList);
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinnerOne.setAdapter(adapter);
        spinnerOne.setOnItemSelectedListener(oneListener);
    }
    // listener for spinner - sets values
    private final AdapterView.OnItemSelectedListener oneListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            setTitles(position);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    };

    private void setTitles(int position){
        personImg.setImageResource(peopleList.get(position).getImg());
        selectedPersonName.setText(getString(R.string.nameLabelText, peopleList.get(position).toString()));
        selectedPersonDate.setText(getString(R.string.dateLabelText, peopleList.get(position).getBirthday()));
        selectedPersonId.setText(getString(R.string.idLabelText, String.valueOf(peopleList.get(position).getID())));
        selectedPersonDescription.setText(peopleList.get(position).getDescription());
    }
}
