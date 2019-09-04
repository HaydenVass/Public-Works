//Hayden Vass
//Jav2 1905
//GridFragment
package com.example.vasshayden_ce03a.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.example.vasshayden_ce03a.MainActivity;
import com.example.vasshayden_ce03a.R;
import com.example.vasshayden_ce03a.URIadapter;
import com.example.vasshayden_ce03a.uitls.IOUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class GridFragment extends Fragment {
    
    private forResult listener;

    public GridFragment() {
    }

    public static GridFragment newInstance(int launchID) {
        Bundle args = new Bundle();
        args.putInt("LAUNCH_ID", launchID);
        GridFragment fragment = new GridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof forResult){
            listener = (forResult)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        //gets all file Uri from folder and binds adapter
        ArrayList<Uri> allURIS = IOUtils.getUris(Objects.requireNonNull(getContext()), MainActivity.IMAGE_FOLDER, MainActivity.AUTHORITY);
        GridView thisGridView = Objects.requireNonNull(getActivity()).findViewById(R.id.img_grid_view);
        thisGridView.setOnItemClickListener(gridClicked);
        if(allURIS != null){
            URIadapter urIadapter = new URIadapter(getContext(), allURIS);
            thisGridView.setAdapter(urIadapter);
        }

    }

    private final AdapterView.OnItemClickListener gridClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //check how activity was launched with: 0 from app, 1 from activity
            int launchId = Objects.requireNonNull(getArguments()).getInt("LAUNCH_ID");
            ArrayList<Uri> allURIS = IOUtils.getUris(Objects.requireNonNull(getContext()), MainActivity.IMAGE_FOLDER, MainActivity.AUTHORITY);
            Uri selectedImage = allURIS.get(position);

            if(launchId == 0){
                //if app was opened by user, the camera g4ts brought up
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(selectedImage, "image/*");
                startActivity(intent);
            }else{
                //if app was opened from App B, the selecgted Uri is converted to a string and
                //sent back to app b
                listener.closeIfLaunchedFromB(selectedImage);
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.camera_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Intent for Camera
        if(item.getItemId() == R.id.action_take_picture){
            // make intent
            Intent takePicutre = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //make URI
            Uri imageURI = IOUtils.getOutputUri(getUniqueString(), getContext());
            takePicutre.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
            takePicutre.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(takePicutre, MainActivity.REQUEST_TAKE_PICTURE);
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //refreshesa adapter when an image is taken
        // takes in folder name, and authority  and gets all Uris from the files in that folder
        GridView thisGridView = Objects.requireNonNull(getActivity()).findViewById(R.id.img_grid_view);
        ArrayList<Uri> allURIS = IOUtils.getUris(Objects.requireNonNull(getContext()), MainActivity.IMAGE_FOLDER, MainActivity.AUTHORITY);
        if(allURIS != null){
            URIadapter urIadapter = new URIadapter(getContext(), allURIS);
            thisGridView.setAdapter(urIadapter);
        }

    }

    //appends unique value to end of the myimage string
    private String getUniqueString(){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        return format.format(today);
    }

    public interface forResult{
        void closeIfLaunchedFromB(Uri selectedUri);
    }

}
