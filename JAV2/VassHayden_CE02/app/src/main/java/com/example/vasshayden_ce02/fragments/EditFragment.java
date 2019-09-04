//Hayden Vass
//Jav2 - 1905
//edit fragment
package com.example.vasshayden_ce02.fragments;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasshayden_ce02.R;
import com.example.vasshayden_ce02.utils.DatabaseHelper;

import java.util.Calendar;
import java.util.Objects;

public class EditFragment extends Fragment {

    private employeeEdited listener;

    public EditFragment() {
    }

    public static EditFragment newInstance(int ID) {

        Bundle args = new Bundle();
        args.putInt("SELECTED_EMPLOYEE", ID);
        EditFragment fragment = new EditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof employeeEdited){
            listener = (employeeEdited)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        Button addDate = Objects.requireNonNull(getView()).findViewById(R.id.date_button_edit);
        addDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                final int day = c.get(Calendar.DAY_OF_MONTH);
                final int month = c.get(Calendar.MONTH);
                final int year = c.get(Calendar.YEAR);

                //constructs date time picker
                final DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        TextView date = Objects.requireNonNull(getView()).findViewById(R.id.textView_form_hiredate_edit);
                        String yearStr = String.valueOf(year);
                        String monthStr = String.valueOf(month);
                        //if selected day or month doesnt not include a 0, one gets appended as
                        //the first number for format consistency
                        if(monthStr.toCharArray().length == 1){
                            monthStr = "0"+monthStr;
                        }
                        String dayStr = String.valueOf(dayOfMonth);
                        if(dayStr.toCharArray().length == 1){
                            dayStr = "0"+dayStr;
                        }
                        String timeToAdd = dayStr+"-"+monthStr +"-"+yearStr;

                        date.setText(timeToAdd);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //gets selected employee row from args
        int _id = Objects.requireNonNull(getArguments()).getInt("SELECTED_EMPLOYEE");
        //pull from data source and set values
        DatabaseHelper dbh = DatabaseHelper.getInstance(getActivity());
        Cursor c = dbh.getAllData();
        Cursor selectedEmployee = null;
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            int check = c.getInt(c.getColumnIndex("_id"));
            if(check == _id){
                selectedEmployee = c;
            }
        }

        //binds and sets UI elements
        EditText firstName = getView().findViewById(R.id.editText_firstName_edit);
        EditText lastName = getView().findViewById(R.id.editText_lastName_edit);
        EditText employeeNum = getView().findViewById(R.id.editText_EmployeeNumber_edit);
        EditText employeeStatus = getView().findViewById(R.id.editText_status_edit);
        TextView selectedDAte = getView().findViewById(R.id.textView_form_hiredate_edit);

        String fName = selectedEmployee.getString(Objects.requireNonNull(selectedEmployee).getColumnIndex("firstName"));
        String lname = selectedEmployee.getString(selectedEmployee.getColumnIndex("lastName"));
        String eNum = String.valueOf(c.getInt((selectedEmployee.getColumnIndex("employeeNumber"))));
        String eStatus = selectedEmployee.getString(selectedEmployee.getColumnIndex("status"));
        String eHireDate = selectedEmployee.getString(selectedEmployee.getColumnIndex("hireDate"));

        firstName.setText(fName);
        lastName.setText(lname);
        employeeNum.setText(eNum);
        employeeStatus.setText(eStatus);
        selectedDAte.setText(eHireDate);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.form_delete).setVisible(false);
        menu.findItem(R.id.form_edit).setVisible(false);
        menu.findItem(R.id.form_add_employee).setVisible(false);
        menu.findItem(R.id.form_settings).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId  == R.id.form_save){
            if(getView() != null){
                // save  button click
                EditText firstName = getView().findViewById(R.id.editText_firstName_edit);
                EditText lastName = getView().findViewById(R.id.editText_lastName_edit);
                EditText employeeNum = getView().findViewById(R.id.editText_EmployeeNumber_edit);
                EditText employeeStatus = getView().findViewById(R.id.editText_status_edit);
                TextView selectedDAte = getView().findViewById(R.id.textView_form_hiredate_edit);

                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String eNumStr = employeeNum.getText().toString();
                Integer eNum = null;
                if(!eNumStr.equals("")){
                    eNum = Integer.valueOf(eNumStr);
                }
                String eStatus = employeeStatus.getText().toString();
                String hireDate = selectedDAte.getText().toString();

                DatabaseHelper dbh = DatabaseHelper.getInstance(getActivity());
                //validate no feilds were left empty
                if(fName.equals("") || fName.trim().equals("") || lName.equals("") || lName.trim().equals("")||
                        eNum == null || eStatus.equals("") || eStatus.trim().equals("") || hireDate.equals("") ||
                        hireDate.trim().equals("")){
                    Toast.makeText(getContext(), "Please make sure all fields are filled", Toast.LENGTH_LONG).show();
                }else{
                    //puts new values into DB
                        ContentValues cv = new ContentValues();
                        cv.put("firstName",fName); //These Fields should be your String values of actual column names
                        cv.put("lastName",lName);
                        cv.put("employeeNumber",eNum);
                        cv.put("status",eStatus);
                        cv.put("hireDate", selectedDAte.getText().toString());

                        int pulledInt = Objects.requireNonNull(getArguments()).getInt("SELECTED_EMPLOYEE");
                        dbh.updateEmployee(pulledInt, cv);
                        listener.Edited();
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.list_fragment_container,
                                MainListFragment.newInstance()).addToBackStack(null).commit();

                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //launches toast from main activity
    public interface employeeEdited{
        void Edited();
    }

}
