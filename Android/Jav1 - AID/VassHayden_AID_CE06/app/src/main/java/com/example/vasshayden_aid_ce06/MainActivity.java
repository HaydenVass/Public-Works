package com.example.vasshayden_aid_ce06;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.vasshayden_aid_ce06.Fragments.AddContactFragment;
import com.example.vasshayden_aid_ce06.Fragments.ContactsFragment;
import com.example.vasshayden_aid_ce06.Objects.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddContactFragment.ContactsListener {

    private ArrayList<Contact> usersContacats;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(fabBtnPressed);
        usersContacats = new ArrayList<>();
        //inital fragment here
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ContactsFragment.newInstance(usersContacats)).commit();
        //show hide fab
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
    public void setContact(Contact mainContact) {
        usersContacats.add(mainContact);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container, ContactsFragment.newInstance(usersContacats)).commit();
        fab.show();

    }

    private final View.OnClickListener fabBtnPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out).addToBackStack(null).
                    replace(R.id.fragment_container, AddContactFragment.newInstance()).commit();
        }
    };
}
