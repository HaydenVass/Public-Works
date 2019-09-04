package com.example.aid_day6_notes;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;

import com.example.aid_day6_notes.fragments.FormFragment;
import com.example.aid_day6_notes.fragments.MainFragment;
import com.example.aid_day6_notes.objects.Name;

import java.text.Normalizer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FormFragment.NameListener {

    ArrayList<Name> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assignment "names"
        names = new ArrayList<>();

        //inital fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, MainFragment.newInstance(names)).commit();

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                    fab.hide();
                }else{
                    fab.show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out).
                add(R.id.fragmentContainer, FormFragment.newInstance()).
                        addToBackStack(null).commit();

    }

    @Override
    public void setName(String name) {
        // add name from other fragment with interface
        names.add(new Name(name));

        // when adding
        getSupportFragmentManager().popBackStack();

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentContainer, MainFragment.newInstance(names)).commit();

        //reload main fragment list
        // when replacing in the above method
//        getSupportFragmentManager().beginTransaction().replace
//                (R.id.fragmentContainer, MainFragment.newInstance(names)).commit();



    }
}
