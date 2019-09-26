package com.example.vasshayden_ce07.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.vasshayden_ce07.R;

import java.util.ArrayList;
import java.util.Objects;

public class FoundTreasureListFragment extends ListFragment {


    public FoundTreasureListFragment() {
    }

    public static FoundTreasureListFragment newInstance(ArrayList<String> treasures) {
        Bundle args = new Bundle();
        args.putSerializable("FOUND_TREASURE", treasures);
        FoundTreasureListFragment fragment = new FoundTreasureListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.found_items_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //set up list view of found items
        ArrayList<String> items = Objects.requireNonNull(getArguments()).getStringArrayList("FOUND_TREASURE");
        getListView().setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_list_item_1 , Objects.requireNonNull(items)));

    }
}
