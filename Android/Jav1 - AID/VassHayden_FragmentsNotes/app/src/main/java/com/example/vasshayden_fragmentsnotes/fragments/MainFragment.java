package com.example.vasshayden_fragmentsnotes.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vasshayden_fragmentsnotes.R;

public class MainFragment extends Fragment implements  View.OnClickListener{

    public MainFragment() {
        //default constructor
    }

    //factory meethod for creating new fragment
    public static MainFragment newInstance() {
        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    //inflate the layout for the fragment (thats it!)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // all that will ever bre here
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getView() != null){
            Button toastButton = getView().findViewById(R.id.button_toast);
            toastButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if(getView() != null){
            EditText toastTExt = getView().findViewById(R.id.editText_toast);
            String texttoToast = toastTExt.getText().toString();

            View contextView = getView().findViewById(R.id.button_toast);
            Snackbar.make(contextView, texttoToast, Snackbar.LENGTH_SHORT).show();
        }
    }
}
