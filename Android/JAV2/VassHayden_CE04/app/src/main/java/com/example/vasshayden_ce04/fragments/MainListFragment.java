//Hayden Vass
//Jav 2 - 1905
//main list fragment
package com.example.vasshayden_ce04.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vasshayden_ce04.adapter.ContactsAdapter;
import com.example.vasshayden_ce04.objects.People;
import com.example.vasshayden_ce04.R;
import com.example.vasshayden_ce04.utils.ContactUtils;

import java.util.ArrayList;
import java.util.Objects;

public class MainListFragment extends ListFragment {
    public MainListFragment() {
    }

    public static MainListFragment newInstance(ArrayList<String> uniqueIDList) {
        //passed list of unique ids then placed in the arguments
        Bundle args = new Bundle();
        args.putStringArrayList("LIST", uniqueIDList);
        MainListFragment fragment = new MainListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //take in passed arraylist <string> of unique IDs and convert them to people objects
        ArrayList<String>contactIDs = Objects.requireNonNull(getArguments()).getStringArrayList("LIST");
        if(contactIDs != null){
            //gets list from arguments then takes those IDs, creates people objects and pulls contact data
            // to assign to those objects
            ArrayList<People> contactList = getAllContacts(getContext(), contactIDs);
            if (contactList != null){
                ContactsAdapter contactsAdapter = new ContactsAdapter(getContext(), contactList);
                setListAdapter(contactsAdapter);
            }
            // loads details fragment
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.details_container,
                    DetailsFragment.newInstance(Objects.requireNonNull(contactList).get(0).getiD())).commit();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        People selectedPerson = (People)getListAdapter().getItem(position);
        //load details fragment
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.details_container,
                DetailsFragment.newInstance(selectedPerson.getiD())).commit();
    }


    //converst IDs to people objects
    private ArrayList<People> getAllContacts(Context c, ArrayList<String> allUniqueIds){
        ArrayList<People> allContacts = new ArrayList<>();
        for (String id: allUniqueIds) {
            People personToAdd = new People(id);
            ContactUtils.getNames(c, personToAdd);
            ContactUtils.getPhoneNumbers(c, personToAdd);
            ContactUtils.getPhoto(c, personToAdd);
            allContacts.add(personToAdd);
        }
        return allContacts;
    }

}
