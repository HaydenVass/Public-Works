package com.example.aid_final_day.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aid_final_day.R;
import com.example.aid_final_day.objects.AndroidVersion;

import java.util.ArrayList;

public class MainFragment extends ListFragment {

    private VersionClickedListener listener;

    //key for arbument
    private final static String ARG_VERSIONS = "ARG_VERSIONS";

    public MainFragment() {
    }

    public static MainFragment newInstance(ArrayList<AndroidVersion> versions) {

        //crearte bundle and set arguments as key/value pair
        Bundle args = new Bundle();
        args.putSerializable(ARG_VERSIONS, versions);

        //create and return fragment with arguments
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof VersionClickedListener){
            listener = (VersionClickedListener)context;
        }
    }

    //inflate fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container, false);
    }

    // after onCreate of the launching Activity is complete...
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null){
            ArrayList<AndroidVersion> versions = (ArrayList<AndroidVersion>) getArguments().
                    getSerializable(ARG_VERSIONS);
            ArrayAdapter<AndroidVersion> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_expandable_list_item_1,
                    versions
            );
            setListAdapter(adapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        listener.toastVersion(v, position);
    }

    public interface VersionClickedListener {
        void toastVersion(View v, int position);
    }
}
