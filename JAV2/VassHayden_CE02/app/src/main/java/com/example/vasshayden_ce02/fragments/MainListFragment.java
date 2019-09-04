//Hayden Vass
//Jav2 - 1905
//main list fragment
package com.example.vasshayden_ce02.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.vasshayden_ce02.R;
import com.example.vasshayden_ce02.custom_adapter.EmployeeAdapter;
import com.example.vasshayden_ce02.utils.DatabaseHelper;

import java.util.Objects;

public class MainListFragment extends ListFragment {

    private SelectedUser listener;

    public MainListFragment() {
    }

    public static MainListFragment newInstance() {

        Bundle args = new Bundle();
        MainListFragment fragment = new MainListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SelectedUser){
            listener = (SelectedUser)context;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // perform query in fragment.
        DatabaseHelper dbh = DatabaseHelper.getInstance(getActivity());
        Cursor c = dbh.getAllData();
        // show tool bar
        setHasOptionsMenu(true);
        //construct spinners
        setUpSpinner(dbh);
        setUpSpinner2(dbh);
        // set adapter

            EmployeeAdapter employeeAdapter = new EmployeeAdapter(getActivity(), c);
            getListView().setAdapter(employeeAdapter);
    }

    //set menu items
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.form_delete).setVisible(false);
        menu.findItem(R.id.form_edit).setVisible(false);
        menu.findItem(R.id.form_save).setVisible(false);
    }

    //button click for spinner items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.form_add_employee:
                // present form fragment
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_fragment_container, FormFragment.newInstance()).
                        addToBackStack(null).commit();
                break;
            case R.id.form_settings:
                //present settings fragment
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_fragment_container, new MainPreferencesFragment()).
                        addToBackStack(null).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        listener.itemSelectedEmployee((int) id);

    }

    //spinner for ASC / DESC
    private void setUpSpinner(final DatabaseHelper dbh){
        // construction for UI spinner
        Spinner sOne = Objects.requireNonNull(getView()).findViewById(R.id.spinner_sort_type);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource
                (Objects.requireNonNull(this.getActivity()), R.array.spinner_sort_direction, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sOne.setAdapter(adapter1);
        sOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner filterBySpinner = Objects.requireNonNull(getView()).findViewById(R.id.spinner_sort_subject);
                //checks which item is selected from adjacent spinner
                int checkType = filterBySpinner.getSelectedItemPosition();
                switch (position){
                    case 0:
                        //descending
                        if(checkType == 0){
                            refreshListViewDirection("DESC", dbh);
                        }else{ refreshListViewSubject("DESC", dbh);}
                        break;
                    case 1:
                        if(checkType == 0){
                            refreshListViewDirection("ASC", dbh);
                        }else{ refreshListViewSubject("ASC", dbh);}
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    //reloads adapter per spinner options

    private void refreshListViewDirection(String direction, DatabaseHelper dbh){
        Cursor cursor1 = dbh.getAllDataFilterNum(direction);
        EmployeeAdapter employeeAdapter2 = new EmployeeAdapter(getActivity(), cursor1);
        getListView().setAdapter(employeeAdapter2);
    }
    //reloads adapter per spinner options
    private void refreshListViewSubject(String direction, DatabaseHelper dbh){
        Cursor cursor1 = dbh.getAllDataFilterStatus(direction);
        getListView().destroyDrawingCache();
        EmployeeAdapter employeeAdapter2 = new EmployeeAdapter(getActivity(), cursor1);
        getListView().setAdapter(employeeAdapter2);
    }
    // spinner for sorting by ID or status
    private void setUpSpinner2(final DatabaseHelper dbh){
        // construction for UI spinner
        Spinner sOne = Objects.requireNonNull(getView()).findViewById(R.id.spinner_sort_subject);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource
                (Objects.requireNonNull(this.getActivity()), R.array.spinner_sort_options, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sOne.setAdapter(adapter1);
        sOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //check current ID of the other spinner and determine what to filter by that as well
                //as which state this spinner is in.
                Spinner filterBySpinner = Objects.requireNonNull(getView()).findViewById(R.id.spinner_sort_type);
                int checkType = filterBySpinner.getSelectedItemPosition();
                switch (position){
                    case 0:
                        if(checkType == 0){
                            refreshListViewDirection("DESC", dbh);
                        }else{refreshListViewDirection("ASC", dbh);}

                    break;
                    case 1:
                        if(checkType == 0){
                            refreshListViewSubject("DESC", dbh);
                        }else{refreshListViewSubject("ASC", dbh);}
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public interface SelectedUser{
        void itemSelectedEmployee(int employeeRow);
    }
}
