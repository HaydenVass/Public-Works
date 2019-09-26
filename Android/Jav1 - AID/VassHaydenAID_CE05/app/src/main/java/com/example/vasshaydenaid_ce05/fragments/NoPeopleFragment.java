package com.example.vasshaydenaid_ce05.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.vasshaydenaid_ce05.R;

public class NoPeopleFragment extends Fragment {


    public NoPeopleFragment() {
    }

    // new instance method
    public static NoPeopleFragment newInstance() {
        Bundle args = new Bundle();

        NoPeopleFragment fragment = new NoPeopleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.no_people_to_add_fragment, container, false);
    }

}
