package com.example.vasshaydenaid_ce05.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.vasshaydenaid_ce05.Interfaces.PassPeople;
import com.example.vasshaydenaid_ce05.Objects.Teacher;
import com.example.vasshaydenaid_ce05.R;

import java.util.Objects;

public class InstructorFormFragment extends Fragment {

    private PassPeople peoplePasser;

    public InstructorFormFragment() {
    }

    public static InstructorFormFragment newInstance() {

        Bundle args = new Bundle();

        InstructorFormFragment fragment = new InstructorFormFragment();
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
        return inflater.inflate(R.layout.instructor_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            Button addButton = getView().findViewById(R.id.add_instructor_btn);
            addButton.setOnClickListener(add_btn_pressed);
        }
    }

    private final View.OnClickListener add_btn_pressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
                Toast.makeText(getActivity(),getString(R.string.empty_fields),Toast.LENGTH_SHORT).show();

            }else{
                peoplePasser.setNewPerson(new Teacher(firstNameStr, lastNameStr, programStr));
                firstName.setText("");
                lastName.setText("");
                program.setText("");
                //toast to signal succsess.
                Toast.makeText(getActivity(),getString(R.string.added_instructor),Toast.LENGTH_SHORT).show();
            }

        }
    };
}
