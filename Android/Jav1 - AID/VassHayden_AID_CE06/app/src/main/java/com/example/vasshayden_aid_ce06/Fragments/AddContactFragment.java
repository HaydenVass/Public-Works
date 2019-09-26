package com.example.vasshayden_aid_ce06.Fragments;

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

import com.example.vasshayden_aid_ce06.Objects.Contact;
import com.example.vasshayden_aid_ce06.R;

import java.util.Objects;

public class AddContactFragment extends Fragment {

    private ContactsListener listener;


    public AddContactFragment() {
    }

    public static AddContactFragment newInstance() {
        Bundle args = new Bundle();
        AddContactFragment fragment = new AddContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ContactsListener){
            listener = (ContactsListener)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getView() != null){
            Button addBtn = getView().findViewById(R.id.add_btn);
            addBtn.setOnClickListener(add_btn_pressed);
        }
    }

    private final View.OnClickListener add_btn_pressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText firstNameET = Objects.requireNonNull(getView()).findViewById(R.id.first_name);
            String fristNameStr = firstNameET.getText().toString();

            EditText lastNameEt = getView().findViewById(R.id.last_name);
            String lastNameStr = lastNameEt.getText().toString();

            EditText phoneNumEt = getView().findViewById(R.id.phone_num);
            String phoneNumStr = phoneNumEt.getText().toString();

            if(fristNameStr.equals("") || lastNameStr.equals("") || phoneNumStr.equals("") ||
                    fristNameStr.trim().equals("") || lastNameStr.trim().equals("") || phoneNumStr.trim().equals("")){
                Toast.makeText(getActivity(),getString(R.string.no_empty),Toast.LENGTH_SHORT).show();
            }else{

                Contact contactToAdd = new Contact(fristNameStr, lastNameStr, phoneNumStr);
                listener.setContact(contactToAdd);

                firstNameET.setText("");
                lastNameEt.setText("");
                phoneNumEt.setText("");
            }
        }
    };

    public interface ContactsListener{
        void setContact(Contact newContact);
    }
}
