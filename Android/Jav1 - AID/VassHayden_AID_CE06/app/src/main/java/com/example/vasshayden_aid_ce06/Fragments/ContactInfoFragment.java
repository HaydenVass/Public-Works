package com.example.vasshayden_aid_ce06.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vasshayden_aid_ce06.Objects.Contact;
import com.example.vasshayden_aid_ce06.R;

import java.util.Objects;

public class ContactInfoFragment extends Fragment{

    private Contact passedContact;

    public ContactInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String firstName = (String) Objects.requireNonNull(getArguments()).get("firstName");
        String lastName = (String) getArguments().get("lastName");
        String phoneNum = (String) getArguments().get("phoneNum");

        passedContact = new Contact(firstName, lastName, phoneNum);

        return inflater.inflate(R.layout.contact_info_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        passedContact = new Contact(passedContact.getFirstName(), passedContact.getLastName(), passedContact.getPhoneNum());
        if(getView() != null){
            Button backBtn = getView().findViewById(R.id.back_btn);
            backBtn.setOnClickListener(backBtnPressed);

            TextView firstName = getView().findViewById(R.id.first_name_tv);
            TextView lastName = getView().findViewById(R.id.last_name_tv);
            TextView phoneNum = getView().findViewById(R.id.phone_num_tv);

            firstName.setText(passedContact.getFirstName());
            lastName.setText(passedContact.getLastName());
            phoneNum.setText(passedContact.getPhoneNum());
        }
    }

    private final View.OnClickListener backBtnPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
        }
    };

}
