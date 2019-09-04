package com.example.aid_final_day.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.aid_final_day.R;
import com.example.aid_final_day.objects.AndroidVersion;

public class FormFragment extends Fragment {

    AndroidVersionListner listener;
    private static final String TAG = "formfrag";

    public FormFragment() {
    }

    public static FormFragment newInstance() {

        Bundle args = new Bundle();

        FormFragment fragment = new FormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof AndroidVersionListner){
            listener = (AndroidVersionListner)context;
        }else{
            Log.e(TAG, "onAttach: Form activity must implient android version listener");
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
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_form, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.form_add_version){
            if(getView() != null){

                EditText editTextVersionName = getView().findViewById(R.id.editText_versionname);
                EditText editTextVersionNumber = getView().findViewById(R.id.editText_versionnumber);

                //get input
                String versionName = editTextVersionName.getText().toString();
                String versionNumberText = editTextVersionNumber.getText().toString();
                //change to float
                float versionNumber = Float.parseFloat(versionNumberText);

                //create new object
                AndroidVersion androidVersion =  new AndroidVersion(versionNumber, versionName);

                // pass android version back to form activity
                listener.addAndroidVersion(androidVersion);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public interface AndroidVersionListner{
        void addAndroidVersion(AndroidVersion version);
    }
}
