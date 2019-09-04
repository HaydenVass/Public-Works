//Hayden Vass
//Jav2 - 1905
//form fragment
package com.example.vasshayden_ce02.fragments;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class FormFragment extends Fragment{

    private EmployeeAdded listener;

    private static final String TAG = "today";

    //Edit / form fragments are essentially the same - but its easier for me to
    //keep track of them this way.
    public FormFragment() {
    }

    public static FormFragment newInstance() {
        Bundle args = new Bundle();
        FormFragment fragment = new FormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof EmployeeAdded){
            listener = (EmployeeAdded)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_fragment, container, false);
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

                //constructs date time picker and formats
                final DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        TextView date = Objects.requireNonNull(getView()).findViewById(R.id.textView_form_hiredate_edit);
                        String yearStr = String.valueOf(year);
                        String monthStr = String.valueOf(month);
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
                Log.i(TAG, "onOptionsItemSelected: ");

                DatabaseHelper dbh = DatabaseHelper.getInstance(getActivity());
                // save  button click
                EditText firstName = getView().findViewById(R.id.editText_firstName_edit);
                EditText lastName = getView().findViewById(R.id.editText_lastName_edit);
                EditText employeeNum = getView().findViewById(R.id.editText_EmployeeNumber_edit);
                EditText employeeStatus = getView().findViewById(R.id.editText_status_edit);
                TextView selectedDAte = getView().findViewById(R.id.textView_form_hiredate_edit);

                String dateFormat = getDatePrefrence(getContext());

                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String eNumStr = employeeNum.getText().toString();
                Integer eNum = null;
                if(!eNumStr.equals("")){
                    eNum = Integer.valueOf(eNumStr);
                }
                String eStatus = employeeStatus.getText().toString();
                String hireDate = selectedDAte.getText().toString();


                //validate no feilds were left empty
                if(fName.equals("") || fName.trim().equals("") || lName.equals("") || lName.trim().equals("")||
                 eNum == null || eStatus.equals("") || eStatus.trim().equals("") || hireDate.equals("") ||
                hireDate.trim().equals("")){
                    Toast.makeText(getContext(), "Please make sure all fields are filled", Toast.LENGTH_LONG).show();
                }else{
                    if(dbh.checkENumExist(eNum)){
                        Toast.makeText(getContext(), "That employee number already exist. Pick another one.", Toast.LENGTH_LONG).show();
                    }else{
                        ContentValues cv = new ContentValues();
                        cv.put("firstName",fName);
                        cv.put("lastName",lName);
                        cv.put("employeeNumber",eNum);
                        cv.put("status",eStatus);
                        cv.put("hireDate", hireDate);

                        // adds new employee to DB
                        dbh.insertEmployee(cv);
                        listener.addEmployee();

                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.list_fragment_container,
                                MainListFragment.newInstance()).addToBackStack(null).commit();
                    }
                }
            }
        }
            return super.onOptionsItemSelected(item);
        }

    private static String getDatePrefrence(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("list_preference", null);
    }

      //interface to send new employee back
    public interface EmployeeAdded{
        void addEmployee();
    }
}
