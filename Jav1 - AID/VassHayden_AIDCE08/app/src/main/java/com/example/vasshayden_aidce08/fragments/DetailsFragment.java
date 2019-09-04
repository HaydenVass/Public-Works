package com.example.vasshayden_aidce08.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.example.vasshayden_aidce08.R;
import com.example.vasshayden_aidce08.objects.Monster;

import java.util.Objects;

public class DetailsFragment extends Fragment {
    private static final String TAG = "today";
    private DeleteMonster listener;

    public DetailsFragment() {
    }

    public static DetailsFragment newInstance() {

        Bundle args = new Bundle();

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof DeleteMonster){
            listener = (DeleteMonster)context;
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
        return inflater.inflate(R.layout.details_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.form_add_munster).setVisible(false);
        //set menu items
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent starter = (Objects.requireNonNull(getActivity())).getIntent();
        if(starter != null){
            TextView monsterNameTV = Objects.requireNonNull(getView()).findViewById(R.id.selectedmonster_name);
            TextView hasFurTV = getView().findViewById(R.id.selectedMonster_fur);
            TextView monsterLegsTV = getView().findViewById(R.id.selectedmonster_legs);

            //catch selected monster
            Monster receivedMonster = (Monster) starter.getSerializableExtra("SELECTED_MONSTER");
            //assign values to text views
            monsterNameTV.setText(receivedMonster.getName());
            monsterLegsTV.setText(receivedMonster.getNumberOfLegs());
            hasFurTV.setText(receivedMonster.getHasFurStr());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //if item id == delete - then get values from text views and send back to delete or share
        int itemID = item.getItemId();
        Intent starter;
        Monster recievedMonster = null;

        if(getView() != null){
            starter = (Objects.requireNonNull(getActivity())).getIntent();
            recievedMonster = (Monster) starter.getSerializableExtra("SELECTED_MONSTER");
        }

        if(recievedMonster != null){
            if(itemID == R.id.form_delete){
                listener.removeMonster(recievedMonster);


            }else if (itemID == R.id.form_add_share){
                Log.i(TAG, "onOptionsItemSelected: share");
                listener.shareMonster(recievedMonster);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public interface DeleteMonster{
        void removeMonster(Monster monster);
        void shareMonster(Monster monster);
    }
}
