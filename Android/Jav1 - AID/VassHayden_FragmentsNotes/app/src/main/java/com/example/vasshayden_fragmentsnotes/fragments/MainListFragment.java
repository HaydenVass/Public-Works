package com.example.vasshayden_fragmentsnotes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.vasshayden_fragmentsnotes.R;

import java.util.ArrayList;

public class MainListFragment extends ListFragment {

    private static final String ARG_DESSERTES = "ARG_DESSERTS";

    public MainListFragment() {
        //default constructor
    }

    public static MainListFragment newInstance(ArrayList<String> desserts) {

        Bundle args = new Bundle();
        args.putStringArrayList(ARG_DESSERTES, desserts);

        MainListFragment fragment = new MainListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null){
            ArrayList<String> desserts = getArguments().getStringArrayList(ARG_DESSERTES);
            ArrayAdapter adapter = new ArrayAdapter<String>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    desserts
            );
            setListAdapter(adapter);
        }
    }
}
