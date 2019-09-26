package com.example.vasshaydenaid_ce05.fragments;

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
import android.widget.Toast;

import com.example.vasshaydenaid_ce05.Interfaces.PassPeople;
import com.example.vasshaydenaid_ce05.Objects.Administrator;
import com.example.vasshaydenaid_ce05.R;

import java.util.Objects;

public class AdministratorFormFragment extends Fragment {
    private static final String TAG = "FormFragment";
    private PassPeople peoplePasser;


    public AdministratorFormFragment() {
    }

    public static AdministratorFormFragment newInstance() {

        Bundle args = new Bundle();

        AdministratorFormFragment fragment = new AdministratorFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof PassPeople){
            peoplePasser = (PassPeople)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.administrator_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getView() != null){
            Button addButton = getView().findViewById(R.id.add_admin_btn);
            addButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: admin clicked");
                    EditText firstName = Objects.requireNonNull(getView()).findViewById(R.id.first_name);
                    EditText lastName = getView().findViewById(R.id.last_name);
                    EditText program = getView().findViewById(R.id.suplimentary_text);

                    String firstNameStr = firstName.getText().toString();
                    String lastNameStr = lastName.getText().toString();
                    String programStr = program.getText().toString();

                    //validation
                    if(firstNameStr.equals("") || lastNameStr.equals("") || programStr.equals("") ||
                            firstNameStr.trim().equals("") || lastNameStr.trim().equals("") ||
                            programStr.trim().equals("")){
                        //toast to signal failure
                        Toast.makeText(getActivity(),getString(R.string.empty_fields),Toast.LENGTH_SHORT).show();
                    }else{

                        peoplePasser.setNewPerson(new Administrator(firstNameStr, lastNameStr, programStr));
                        firstName.setText("");
                        lastName.setText("");
                        program.setText("");
                        //toast to signal succsess.
                        Toast.makeText(getActivity(),getString(R.string.added_admin),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
