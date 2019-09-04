package com.example.vasshayden_aid_ce06.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vasshayden_aid_ce06.Objects.Contact;
import com.example.vasshayden_aid_ce06.R;

import java.util.ArrayList;
import java.util.Objects;

public class ContactsFragment extends ListFragment {

    public ContactsFragment() {
    }

    public static ContactsFragment newInstance(ArrayList<Contact> userContracts) {
        Bundle args = new Bundle();
        args.putSerializable("contacts", userContracts);
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null){

            ListView listView = Objects.requireNonNull(getView()).findViewById(android.R.id.list);

            final ArrayList<Contact> userContacts = (ArrayList<Contact>)getArguments().getSerializable("contacts");
            ArrayAdapter<Contact> adapter = new ArrayAdapter<>
                    (Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, Objects.requireNonNull(userContacts));


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                    Bundle bundle = new Bundle();
                    bundle.putString("firstName", userContacts.get(position).getFirstName());
                    bundle.putString("lastName", userContacts.get(position).getLastName());
                    bundle.putString("phoneNum", userContacts.get(position).getPhoneNum());



                    ContactInfoFragment contactInfoFragment = new ContactInfoFragment();
                    contactInfoFragment.setArguments(bundle);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                    getActivity().getSupportFragmentManager().beginTransaction().
                            setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out).addToBackStack(null).
                            add(R.id.fragment_container, contactInfoFragment).commit();
                }
            });

            setListAdapter(adapter);
        }
    }
}
