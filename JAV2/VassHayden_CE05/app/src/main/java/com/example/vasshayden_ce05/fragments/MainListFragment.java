//Hayden Vass
// Jav2 1905
//MainListFragment
package com.example.vasshayden_ce05.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vasshayden_ce05.DataContracts;
import com.example.vasshayden_ce05.R;
import com.example.vasshayden_ce05.adapters.BookAdapter;
import com.example.vasshayden_ce05.utils.NotificationsUtils;

import java.util.Objects;

public class MainListFragment extends ListFragment {

    public MainListFragment() {
    }
    public static MainListFragment newInstance() {
        Bundle args = new Bundle();
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
        return inflater.inflate(R.layout.list_fragment,container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity a = getActivity();
        if(a!=null) {
            //get object form activity
            ContentResolver cr = a.getContentResolver();
            Cursor c = cr.query(DataContracts.PROVIDER_CONTENT_URI, null, null, null, null);
            // load cursor adapter
            BookAdapter bookAdapter = new BookAdapter(getContext(), c, 0);
            getListView().setAdapter(bookAdapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //get cursor for selected row id
        Cursor c = getRow(String.valueOf(v.getTag()));
        if(c != null){
            String description = c.getString(c.getColumnIndex("Description"));
            String title = c.getString(c.getColumnIndex("title"));
            if(description == null){
                description = getString(R.string.no_description);
            }
            NotificationsUtils.presentDialog(getActivity(), title, description, getString(R.string.dismiss));
        }
    }
    //grabs specific row from the providers table
    private Cursor getRow(String id){
        ContentResolver cr = Objects.requireNonNull(getActivity()).getContentResolver();
        Cursor c = cr.query(DataContracts.PROVIDER_CONTENT_URI, null, "_id = " + id,
                null, null);
        if(c!=null){
            c.moveToFirst();
        }
        return c;
    }

}
