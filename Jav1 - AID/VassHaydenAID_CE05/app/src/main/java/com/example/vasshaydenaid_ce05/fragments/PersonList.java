package com.example.vasshaydenaid_ce05.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.vasshaydenaid_ce05.Objects.Person;
import com.example.vasshaydenaid_ce05.R;
import java.util.ArrayList;
import java.util.Objects;

public class PersonList extends ListFragment {

    public PersonList() {
    }

    public static PersonList newInstance(ArrayList<Person> people) {

        Bundle args = new Bundle();
        args.putSerializable("people" , people);
        PersonList fragment = new PersonList();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null){
            ArrayList <Person> people = (ArrayList<Person>)getArguments().getSerializable("people");
            ArrayAdapter<Person> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                    android.R.layout.simple_list_item_1, Objects.requireNonNull(people));
            setListAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}
