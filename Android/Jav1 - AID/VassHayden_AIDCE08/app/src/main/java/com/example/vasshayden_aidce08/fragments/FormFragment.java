package com.example.vasshayden_aidce08.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.example.vasshayden_aidce08.R;
import com.example.vasshayden_aidce08.objects.Monster;

public class FormFragment extends Fragment {

    private MonsterCreatedListener listener;

    public FormFragment() {
    }

    public static FormFragment newInstance() {
        Bundle args = new Bundle();
        FormFragment fragment = new FormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MonsterCreatedListener){
            listener = (MonsterCreatedListener)context;
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
        return inflater.inflate(R.layout.form_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.form_delete).setVisible(false);
        menu.findItem(R.id.form_add_share).setVisible(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if(itemID == R.id.form_add_munster){
            if(getView() != null){
                EditText editTextMonsterName = getView().findViewById(R.id.editTextMonsterName);
                EditText editTextMonsterLegNum = getView().findViewById(R.id.editTextMonsterLegsNum);
                Switch hasFurSwitch = getView().findViewById(R.id.hasFurSwitch);

                String monsterName = editTextMonsterName.getText().toString();
                String monsterLegNumber = editTextMonsterLegNum.getText().toString();
                boolean hasFur = hasFurSwitch.isChecked();

                Monster monsterToAdd = new Monster(monsterName, hasFur, monsterLegNumber);

                listener.monsterCreated(monsterToAdd);

            }
        }


        return super.onOptionsItemSelected(item);
    }

    public interface MonsterCreatedListener{
        void monsterCreated(Monster monster);
    }
}
