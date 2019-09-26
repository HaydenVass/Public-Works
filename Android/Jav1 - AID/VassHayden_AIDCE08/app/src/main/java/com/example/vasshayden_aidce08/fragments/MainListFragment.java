package com.example.vasshayden_aidce08.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vasshayden_aidce08.R;
import com.example.vasshayden_aidce08.objects.Monster;

import java.util.ArrayList;
import java.util.Objects;

public class MainListFragment extends ListFragment {
    private SelectedMonster listener;


    public MainListFragment() {
    }

    public static MainListFragment newInstance(ArrayList<Monster> monsters) {
        //create bundle and set args as key/value
        Bundle args = new Bundle();
        args.putSerializable("MONSTERS", monsters);

        //create and return frament with arguments
        MainListFragment fragment = new MainListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SelectedMonster){
            listener = (SelectedMonster)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null){
            ArrayList<Monster> monsters = (ArrayList<Monster>)getArguments().
                    getSerializable("MONSTERS");
            ArrayAdapter<Monster> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                    android.R.layout.simple_list_item_1, Objects.requireNonNull(monsters));
            setListAdapter(adapter);

        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //logic for details
        Monster selectedMonster = (Monster)getListAdapter().getItem(position);
        //send selected monster as seralizable back to activity the send as intent to details fragment
        listener.itemSelectedMonster(selectedMonster);
    }

    public interface SelectedMonster{
        void itemSelectedMonster(Monster monster);
    }
}
