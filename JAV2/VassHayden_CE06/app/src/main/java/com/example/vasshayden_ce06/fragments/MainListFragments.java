//Hayden Vass
// Jav2 - 1905
//Main List Fragment
package com.example.vasshayden_ce06.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.vasshayden_ce06.R;
import com.example.vasshayden_ce06.Utils.IOUtils;
import com.example.vasshayden_ce06.Utils.NetworkUtils;
import com.example.vasshayden_ce06.adapters.URIadapter;
import com.example.vasshayden_ce06.intent_service.ImageIntentService;

import java.util.ArrayList;
import java.util.Objects;

public class MainListFragments extends Fragment {

    public static final String BROADCAST_ACTION =
            "com.example.vasshayden_ce06.Fragments.BROADCAST_ACTION";
    private final ImageReciever reciever = new ImageReciever();

    public MainListFragments() {
    }

    public static MainListFragments newInstance() {
        Bundle args = new Bundle();
        MainListFragments fragment = new MainListFragments();
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
        return inflater.inflate(R.layout.grid_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //sets up menu bar / assigns onclick to grid view / sets URI adapter for images
        //that already exist
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        GridView mainGrid = Objects.requireNonNull(getView()).findViewById(R.id.image_grid_view);
        mainGrid.setOnItemClickListener(gridClicked);
    }
    //onclick listener to view in image viewer
    private final AdapterView.OnItemClickListener gridClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ArrayList<Uri> allURIS = IOUtils.getUris(Objects.requireNonNull(getContext()));
            Uri selectedImage = allURIS.get(position);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(selectedImage, "image/*");
            startActivity(intent);
        }
    };

    //app bar / menu items
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.download_img){
            if(NetworkUtils.isConnected(Objects.requireNonNull(getActivity()))){
                // download image here
                Intent i = new Intent(getActivity(), ImageIntentService.class);
                getActivity().startService(i);
            }
        }
        return true;
    }

    private void setAdapter(){
        ArrayList<Uri> allUris = IOUtils.getUris(Objects.requireNonNull(getContext()));
        GridView mainGrid = Objects.requireNonNull(getView()).findViewById(R.id.image_grid_view);
        if(allUris != null){
            URIadapter urIadapter = new URIadapter(getContext(), allUris);
            mainGrid.setAdapter(urIadapter);
        }
    }

    //reciever based methods
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION);
        Objects.requireNonNull(getActivity()).registerReceiver(reciever, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getActivity()).unregisterReceiver(reciever);
    }
    //nested reciever class
    class ImageReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null){
                setAdapter();
            }
        }
    }
}
