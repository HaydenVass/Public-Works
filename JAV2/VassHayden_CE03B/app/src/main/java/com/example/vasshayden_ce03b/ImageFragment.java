//Hayden Vass
//Jav2 1905
//image fragment
package com.example.vasshayden_ce03b;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;

import java.io.IOException;
import java.util.Objects;

public class ImageFragment extends Fragment {
    public ImageFragment() {
    }

    public static ImageFragment newInstance() {
        Bundle args = new Bundle();
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.imageview_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.camera_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_view_images){
            // get intent
            Intent getImgIntent = new Intent(Intent.ACTION_PICK);
            getImgIntent.setType("image/*");
            getImgIntent.putExtra("check", 1);
            startActivityForResult(getImgIntent, MainActivity.REQUEST_CODE);
        }
        return true;
    }
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if(requestCode == MainActivity.REQUEST_CODE){
                    if(data != null){
                        //binds img view gets data from intent
                        ImageView iv = Objects.requireNonNull(getView()).findViewById(R.id.img_holder);
                        String uriString = data.getStringExtra(Intent.EXTRA_TEXT);
                        Uri imgUri = Uri.parse(uriString);
                        try {
                            //takes uir from first and converts it to Bitmap - per rubric
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap
                                    (Objects.requireNonNull(getActivity()).getContentResolver(), imgUri);
                            iv.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    }
