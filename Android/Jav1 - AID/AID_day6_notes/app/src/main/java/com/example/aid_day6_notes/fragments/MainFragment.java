package com.example.aid_day6_notes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.aid_day6_notes.R;
import com.example.aid_day6_notes.objects.Name;

import java.util.ArrayList;

public class MainFragment extends ListFragment {

    private static final String ARG_NAMES = "ARG_NAMES";


    public MainFragment() {

    }


    // create new instance method
    public static MainFragment newInstance(ArrayList<Name> names) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_NAMES, names);

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // inflate fragment view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null){
            ArrayList<Name> names = (ArrayList<Name>)getArguments().getSerializable(ARG_NAMES);
            ArrayAdapter<Name>  adapter = new ArrayAdapter<Name>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    names
            );
            setListAdapter(adapter);
        }
    }

}
