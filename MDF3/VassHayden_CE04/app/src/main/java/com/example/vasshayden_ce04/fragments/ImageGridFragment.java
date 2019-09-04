package com.example.vasshayden_ce04.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.vasshayden_ce04.R;
import com.example.vasshayden_ce04.adapters.PhotoAdapter;
import com.example.vasshayden_ce04.utils.PhotoUtils;
import com.example.vasshayden_ce04.widget_flipper.FlipperWidgetHelper;
import com.example.vasshayden_ce04.widget_stack.CollectionWidgetHelper;

import java.util.ArrayList;
import java.util.Objects;

public class ImageGridFragment extends Fragment {

    private static final String TAG = "today";
    private static final int REQUEST_PERMISSION = 0x01002;
    private GridView mGridview;

    public ImageGridFragment() {
    }

    public static ImageGridFragment newInstance() {
        Bundle args = new Bundle();
        ImageGridFragment fragment = new ImageGridFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_fragment, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //get permission to read external storage
        if(ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);

        }else{
            //permission granted - get photo URIS - apply adapter
            //configure frag method so immediate use can be done after granting permissions
            configureFrag();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                //if permission is granted, the photos will load
                configureFrag();
            }else{

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION );
            }
        }
    }

    private void refreshAdapter(){
        PhotoAdapter photoAdapter = new PhotoAdapter(getContext(), PhotoUtils.getImagesPath(Objects.requireNonNull(getContext())));
        mGridview.setAdapter(photoAdapter);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    //updates app information - photos
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh){
            Log.i(TAG, "onOptionsItemSelected: ");
            refreshAdapter();
            FlipperWidgetHelper.notifyDataChanged(getContext());
            CollectionWidgetHelper.notifyDataChanged(getContext());
        }
        return true;
    }

    //populate logic for fragment
    private void configureFrag(){
        setHasOptionsMenu(true);
        mGridview = Objects.requireNonNull(getActivity()).findViewById(R.id.image_gridview);
        refreshAdapter();

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Uri> contentUris = PhotoUtils.getContntImagesPath(Objects.requireNonNull(getContext()));
                Uri selectedImage = contentUris.get(position);
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.setDataAndType(selectedImage, "image/*");
                startActivity(i);

            }
        });
    }
}
