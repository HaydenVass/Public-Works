//Hayden Vass
//Jav2 - 1905
//details frag
package com.example.vasshayden_ce02.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vasshayden_ce02.R;
import com.example.vasshayden_ce02.utils.DatabaseHelper;
import com.example.vasshayden_ce02.utils.TimeFormatUtils;

import java.util.Objects;


public class DetailsFragment extends Fragment {

    private static final String TAG = "today";
    private deletedEmployee listener;

    public DetailsFragment() {
    }

    public static DetailsFragment newInstance(int positionToMoveto) {
        //saves the selected users row in the args bundle
        Bundle args = new Bundle();
        args.putInt("ID", positionToMoveto);
        Log.i(TAG, "newInstance: " + positionToMoveto);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //binds interface
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof deletedEmployee){
            listener = (deletedEmployee)context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_fragment, container, false);
    }

    //shows / hides app bar buttons
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //show / hide app bar buttons
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.form_add_employee).setVisible(false);
        menu.findItem(R.id.form_settings).setVisible(false);
        menu.findItem(R.id.form_save).setVisible(false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        // binds text views
        TextView tv_firstName = Objects.requireNonNull(getView()).findViewById(R.id.textView_selectedFName);
        TextView tv_lastName = getView().findViewById(R.id.textView_selectedLName);
        TextView tv_eNum = getView().findViewById(R.id.textView_selectedENum);
        TextView tv_eStatus = getView().findViewById(R.id.textView_selectedStatus);
        TextView tv_hireDate = getView().findViewById(R.id.tv_hiredate);

        //pull from data source and set values
        //searches through user and the assigns the selected user to the
        //selected employee cursor
        DatabaseHelper dbh = DatabaseHelper.getInstance(getActivity());
        Cursor c = dbh.getAllData();
        Cursor selectedEmployee = null;
        int pulledID = Objects.requireNonNull(getArguments()).getInt("ID");
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            int check = c.getInt(c.getColumnIndex("_id"));
            if(check == pulledID){
                selectedEmployee = c;
                break;
            }
        }
        // pulls values from cursor
        if(selectedEmployee != null){
        String firstName = c .getString(selectedEmployee.getColumnIndex("firstName"));
        String lastName = selectedEmployee.getString(selectedEmployee.getColumnIndex("lastName"));
        String eNum = String.valueOf(c.getInt((selectedEmployee.getColumnIndex("employeeNumber"))));
        String eStatus = selectedEmployee.getString(selectedEmployee.getColumnIndex("status"));
        String eHireDate = selectedEmployee.getString(selectedEmployee.getColumnIndex("hireDate"));
        // assigns them to text view
            tv_firstName.setText(firstName);
            tv_lastName.setText(lastName);
            tv_eNum.setText(eNum);
            tv_eStatus.setText(eStatus);
            tv_hireDate.setText(eHireDate);

        //gets time format from prefrences
            TimeFormatUtils.formatDate(getContext(), eHireDate, tv_hireDate);
        }
    }

    //on clicks for app bar buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.form_delete:
                showDeleteDialog();
                break;
            case R.id.form_edit:
                assert getArguments() != null;
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.list_fragment_container,
                        EditFragment.newInstance(getArguments().getInt("ID"))).addToBackStack(null).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.delete));
        builder.setMessage(getResources().getString(R.string.Are_you_sure));
        builder.setPositiveButton(getResources().getString(R.string.cancel), null);
        builder.setNegativeButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView tv_eNum = Objects.requireNonNull(getView()).findViewById(R.id.textView_selectedENum);
                int eNum = Integer.valueOf(tv_eNum.getText().toString());
                DatabaseHelper dbh = DatabaseHelper.getInstance(getActivity());
                    dbh.deleteEmployee(eNum);
                    listener.deleted();
                    //if the user chooses to delete an employee, fragment gets swapped back to the list fragment
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.list_fragment_container,
                            MainListFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //lets main activity know to present toast
    public interface deletedEmployee{
        void deleted();
    }
}
