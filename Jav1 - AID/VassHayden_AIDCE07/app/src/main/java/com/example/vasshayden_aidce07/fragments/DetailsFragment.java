package com.example.vasshayden_aidce07.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.example.vasshayden_aidce07.R;

import java.util.Objects;

public class DetailsFragment extends Fragment {

    private DeleteUser listener;

    public DetailsFragment() {
    }


    public static DetailsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DeleteUser){
            listener = (DeleteUser)context;
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
        return inflater.inflate(R.layout.person_details_fragment,container,false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_buttons, menu);
        menu.findItem(R.id.action_save).setVisible(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent starter = (Objects.requireNonNull(getActivity())).getIntent();
        if(starter != null){

            TextView firstName = Objects.requireNonNull(getView()).findViewById(R.id.textview_userFirstName);
            TextView lastName = getView().findViewById(R.id.textview_userLastName);
            TextView age = getView().findViewById(R.id.textview_userAge);

            String fNameStr = starter.getStringExtra("com.fullsail.android.jav2ce08.EXTRA_STRING_FIRST_NAME");
            String lNameStr = starter.getStringExtra("com.fullsail.android.jav2ce08.EXTRA_STRING_LAST_NAME");
            int userAge = starter.getIntExtra("com.fullsail.android.jav2ce08.EXTRA_INT_AGE", 0);

            firstName.setText(fNameStr);
            lastName.setText(lNameStr);
            age.setText(String.valueOf(userAge));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.action_delete ){
            if (getView() != null){
                TextView firstName = getView().findViewById(R.id.textview_userFirstName);
                TextView lastName = getView().findViewById(R.id.textview_userLastName);
                TextView age = getView().findViewById(R.id.textview_userAge);

                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                int _age = Integer.parseInt(age.getText().toString());

                listener.removeUser(fName, lName, _age);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public interface DeleteUser{
        void removeUser(String firstName, String lastName, int age);
    }
}
