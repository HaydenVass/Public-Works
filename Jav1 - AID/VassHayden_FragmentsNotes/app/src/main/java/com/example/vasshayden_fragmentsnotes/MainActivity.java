package com.example.vasshayden_fragmentsnotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vasshayden_fragmentsnotes.fragments.MainFragment;
import com.example.vasshayden_fragmentsnotes.fragments.MainListFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> mAndroidVersions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // load standard fragment on launch
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, MainFragment.newInstance()).commit();


        // set up fragment selection spinner
        String[] fragmentListArray = getResources().getStringArray(R.array.fragment_list);
        ArrayList<String> fragmentList = new ArrayList<>(Arrays.asList(fragmentListArray));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                fragmentList
        );

        Spinner fragmentSelector = findViewById(R.id.spinnerFragmentList);
        fragmentSelector.setAdapter(adapter);

        fragmentSelector.setOnItemSelectedListener(this);

        // doo all the stuff for the ListFragment
        String[] androidVersions = getResources().getStringArray(R.array.android_versions);
        mAndroidVersions = new ArrayList<>(Arrays.asList(androidVersions));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      switch (position){
          case 1:
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MainListFragment.newInstance(mAndroidVersions)).commit();
              break;
              default:
                  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance()).commit();

      }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
