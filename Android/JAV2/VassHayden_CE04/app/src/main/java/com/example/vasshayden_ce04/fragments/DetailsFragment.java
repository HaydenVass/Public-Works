//Hayden Vass
//Jav 2 - 1905
//details fragment
package com.example.vasshayden_ce04.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vasshayden_ce04.objects.People;
import com.example.vasshayden_ce04.R;
import com.example.vasshayden_ce04.utils.ContactUtils;

import java.util.Objects;

public class DetailsFragment extends Fragment {

    public DetailsFragment() {
    }

    public static DetailsFragment newInstance(String passedID) {

        Bundle args = new Bundle();
        args.putString("USER_ID", passedID);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //id of clicked user from list fragment
        String userID = Objects.requireNonNull(getArguments()).getString("USER_ID");
        // ui elements
        TextView fullName = Objects.requireNonNull(getView()).findViewById(R.id.details_selected_name);
        ImageView contactPicture = getView().findViewById(R.id.details_contact_img);
        ListView allPhoneNumbersLV = getView().findViewById(R.id.details_all_phoneNums);

        //get User info
        People recievedContact = getContactInfo(userID);

        //set text
        fullName.setText(recievedContact.getFullName());
        
        //sets image
        if(recievedContact.getProfileImage() != null){
            contactPicture.setImageURI(Uri.parse(recievedContact.getProfileImage()));
        }else{
            contactPicture.setImageResource(R.drawable.ic_person);
        }

        // sets all phone number array adapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_expandable_list_item_1, recievedContact.getAllPhoneNums());
        allPhoneNumbersLV.setAdapter(arrayAdapter);
    }

    //takes passed ID, creates a person object and then performs queires to get data from contacts
    private People getContactInfo(String ID){
        People passedPerson = new People(ID);
        ContactUtils.getNames(Objects.requireNonNull(getContext()), passedPerson);
        ContactUtils.getPhoneNumbers(getContext(), passedPerson);
        ContactUtils.getPhoto(getContext(), passedPerson);
        return passedPerson;
    }
}
