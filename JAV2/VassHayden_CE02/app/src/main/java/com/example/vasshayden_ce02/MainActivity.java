//Hayden Vass
//Jav2 - 1905
//main activity
package com.example.vasshayden_ce02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vasshayden_ce02.fragments.DetailsFragment;
import com.example.vasshayden_ce02.fragments.EditFragment;
import com.example.vasshayden_ce02.fragments.FormFragment;
import com.example.vasshayden_ce02.fragments.MainListFragment;



public class MainActivity extends AppCompatActivity implements FormFragment.EmployeeAdded,
        MainListFragment.SelectedUser, EditFragment.employeeEdited, DetailsFragment.deletedEmployee {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.list_fragment_container,
                MainListFragment.newInstance()).addToBackStack(null).commit();
    }

    //manage back stack
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else{
            super.onBackPressed();
        }
    }

    //selected employee from list
    @Override
    public void itemSelectedEmployee(int employeeRow) {
        //gets selected employee from list and launches Details fragment with their rowID in the DB
         getSupportFragmentManager().beginTransaction().replace(R.id.list_fragment_container,
         DetailsFragment.newInstance(employeeRow)).addToBackStack(null).commit();
    }

    @Override
    public void addEmployee() {
        Toast.makeText(this, getResources().getString(R.string.saved), Toast.LENGTH_LONG).show();
    }

    @Override
    public void Edited() {
        Toast.makeText(this, getResources().getString(R.string.edited), Toast.LENGTH_LONG).show();
    }

    @Override
    public void deleted() {
        Toast.makeText(this, getResources().getString(R.string.deleted), Toast.LENGTH_LONG).show();
    }
}
