package com.example.vasshayden_aidce07.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.vasshayden_aidce07.R;


public class FormFragment extends Fragment {

    private AddUserToList listener;

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
        if (context instanceof AddUserToList){
            listener = (AddUserToList)context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_fragment,container,false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_buttons, menu);
        menu.findItem(R.id.action_delete).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.action_save ){
            if (getView() != null){
                EditText firstName = getView().findViewById(R.id.textview_userFirstName);
                EditText lastName = getView().findViewById(R.id.textview_userLastName);
                EditText age = getView().findViewById(R.id.editText_age);

                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                int _age = Integer.parseInt(age.getText().toString());

                listener.addPerson(fName, lName, _age);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public interface AddUserToList{
        void addPerson(String firstName, String lastName, int age);
    }
}
