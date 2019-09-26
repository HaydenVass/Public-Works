package com.example.aid_day6_notes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.aid_day6_notes.R;

public class FormFragment extends Fragment {

    private static final String TAG = "FormFragment";
    NameListener listener;

    public FormFragment() {

    }


    // new instance method
    public static FormFragment newInstance() {

        Bundle args = new Bundle();

        FormFragment fragment = new FormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NameListener){
            listener = (NameListener)context;
        }else{
            Log.i(TAG, "onAttach: " + context.toString() + "must impliment name listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getView() != null){
            Button buttonSetName = getView().findViewById(R.id.buttonName);
            buttonSetName.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    EditText editTextName = getView().findViewById(R.id.editTextName);
                    String name = editTextName.getText().toString();
                    listener.setName(name);
                }
            });
        }
    }

    public interface NameListener{
         void setName(String name);
    }
}
