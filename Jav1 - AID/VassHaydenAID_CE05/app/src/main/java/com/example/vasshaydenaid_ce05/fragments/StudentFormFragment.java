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
import com.example.vasshaydenaid_ce05.Objects.Student;
import com.example.vasshaydenaid_ce05.R;

import java.util.Objects;

public class StudentFormFragment extends Fragment {

    private PassPeople peoplePasser;

    public StudentFormFragment() {
    }

    public static StudentFormFragment newInstance() {

        Bundle args = new Bundle();

        StudentFormFragment fragment = new StudentFormFragment();
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
        return inflater.inflate(R.layout.student_form,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            Button addButton = getView().findViewById(R.id.add_btn_student);
            addButton.setOnClickListener(add_btn_pressed);
        }
    }

    private final View.OnClickListener add_btn_pressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText firstName = Objects.requireNonNull(getView()).findViewById(R.id.first_name);
            EditText lastName = getView().findViewById(R.id.last_name);
            EditText studentID = getView().findViewById(R.id.suplimentary_text);

            String firstNameStr = firstName.getText().toString();
            String lastNameStr = lastName.getText().toString();
            String studentIDStr = studentID.getText().toString();

            //validation
            if(firstNameStr.equals("") || lastNameStr.equals("") || studentIDStr.equals("") ||
                    firstNameStr.trim().equals("") || lastNameStr.trim().equals("") ||
                   studentIDStr.trim().equals("")){
                //toast to signal failure
                Toast.makeText(getActivity(),getString(R.string.empty_fields),Toast.LENGTH_SHORT).show();

            }else{

                peoplePasser.setNewPerson(new Student(firstNameStr, lastNameStr, studentIDStr));
                firstName.setText("");
                lastName.setText("");
                studentID.setText("");
                //toast to signal succsess.
                Toast.makeText(getActivity(),getString(R.string.added_student),Toast.LENGTH_SHORT).show();
            }

        }
    };
}
