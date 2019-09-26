// Hayden Vass
// JAV1 - 1094
// CE04
package com.example.vasshaydence04;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Person> peopleList;
    private ListView mListView;
    private GridView mGridView;
    private Person selectedPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = findViewById(R.id.foo);
        mGridView.setVisibility(View.GONE);
        peopleList = new ArrayList<>();
        createPeopleList();
        setUpSpinnerOne();
        setUpSpinnerTwo();
        findListView();
        setUpArrayAdapterView();
        mListView.setOnItemClickListener(test);
        mGridView.setOnItemClickListener(test);
    }
    // onclick listener for dialog box
    private final AdapterView.OnItemClickListener test = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedPerson = peopleList.get(position);
            showDialog();
        }
    };

    private void findListView() {
        try {
            mListView = findViewById(R.id.cell);
            mGridView = findViewById(R.id.foo);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // assigns custom adapter to list and grid views
    private void setUpBaseAdapter(){
        if(mListView != null){
            PersonAdapter pa = new PersonAdapter(this, peopleList);
            mListView.setAdapter(pa);
            mGridView.setAdapter(pa);
        }
    }
    // constructd array adapter and set them to both list and grid view.
    private void setUpArrayAdapterView(){
        ArrayAdapter<Person> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, peopleList);
        mListView.setAdapter(arrayAdapter);
        mGridView.setAdapter(arrayAdapter);
    }
    // construct simple adapter view
    private void setUpSimpleAdapterView(){
        // creates keys
        final String keyFirstName = "firstName";
        final String keyBirthday = "birthDay";
        // takes person object and creates a hashmap for the simple adapter view
        ArrayList<HashMap<String, String>> dataCollection = new ArrayList<>();
        for (Person person : peopleList) {
            HashMap<String, String> map = new HashMap<>();
            map.put(keyFirstName, person.getFirstName() + " " + person.getLastName());
            map.put(keyBirthday, person.getBirthday());
            dataCollection.add(map);
        }
        // assigns values and views in the same order so they line up
        String[] keys = { keyFirstName, keyBirthday  };
        int[] viewIDs = { R.id.tv_firstName, R.id.tv_lastName };
        SimpleAdapter adapter = new SimpleAdapter(this, dataCollection, R.layout.cell,
                keys, viewIDs );
        mListView.setAdapter(adapter);
        mGridView.setAdapter(adapter);
    }


    // spinner for list view vs grid view
    private void setUpSpinnerOne(){
        Spinner sOne = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource
                (this, R.array.spinner1_values, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sOne.setAdapter(adapter1);
        sOne.setOnItemSelectedListener(oneListener);
    }
    // switch between list and grid  views
    private final AdapterView.OnItemSelectedListener oneListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selection = parent.getItemAtPosition(position).toString();
            switch (selection){
                // toggles views
                case "List View":
                    mGridView.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                    break;
                case "Grid View":
                    mListView.setVisibility(View.GONE);
                    mGridView.setVisibility(View.VISIBLE);
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    };

    //spinner for adapters
    private void setUpSpinnerTwo(){
        Spinner sTwo = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource
                (this, R.array.spinner2_values, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTwo.setAdapter(adapter2);
        sTwo.setOnItemSelectedListener(TwoListener);
    }
    // switch between different adapter views
    private final AdapterView.OnItemSelectedListener TwoListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selection = parent.getItemAtPosition(position).toString();
            switch (selection){
                case "Array Adapter": setUpArrayAdapterView();
                break;
                case "Simple Adapter": setUpSimpleAdapterView();
                break;
                case "Base Adapter": setUpBaseAdapter();
                break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    //creates data to populate list / grid view
    private void createPeopleList(){
        String[] firstNames = {"Margo", "Robbie", "Joe", "John", "Paul", "Ringo", "George", "Ben", "Lindsey", "Chloe"};
        String[] lastNames = {"Smith", "Robs", "Slim","Lennon", "Rick", "Star", "Tod", "Nicholes", "Kay", "Cook"};
        int[] drawables = {R.drawable.girl4, R.drawable.boy, R.drawable.bo1, R.drawable.boy3, R.drawable.girl, R.drawable.girl2,
        R.drawable.girl3, R.drawable.man, R.drawable.man2, R.drawable.man3};
        String [] birthday = {"3/12/1992", "4/6/1930", "8/4/1993", "12/3/1990", "9/22/1984", "7/11/1992"
        ,"5/17/1989", "8/28/1980", "9/4/2000", "1/15/1990"};
        for (int i = 0; i < 10; i++) {
            Person personToAdd = new Person(firstNames[i], lastNames[i], birthday[i], drawables[i]);
            peopleList.add(personToAdd);
        }
    }
        // dialog for list / grid view click
        private void showDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setIcon(selectedPerson.getPicture());
            builder.setTitle(String.valueOf(selectedPerson.toString()));
            builder.setCancelable(false);
            builder.setMessage(selectedPerson.getBirthday());
            builder.setPositiveButton(R.string.close, null);
            builder.show();
        }
}
