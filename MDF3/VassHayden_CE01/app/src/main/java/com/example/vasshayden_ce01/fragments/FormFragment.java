package com.example.vasshayden_ce01.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vasshayden_ce01.MainActivity;
import com.example.vasshayden_ce01.R;
import com.example.vasshayden_ce01.objects.SavedLocation;
import com.example.vasshayden_ce01.utils.FileUtils;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class FormFragment extends Fragment {

    private static final String TAG = "today";
    private FormCallback formCallback;

    private double sentLat = 0.0;
    private double sentLong = 0.0;
    private Bitmap bmp;

    public FormFragment() { }

    public static FormFragment newInstance() {
        Bundle args = new Bundle();
        FormFragment fragment = new FormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        //attach interface
        super.onAttach(context);
        if(context instanceof FormCallback){
            formCallback = (FormCallback)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = Objects.requireNonNull(getActivity()).getIntent().getExtras();
        if (bundle != null){
            LatLng sentLatLong = (LatLng) bundle.get("LatLong");
            sentLat = Objects.requireNonNull(sentLatLong).latitude;
            sentLong = sentLatLong.longitude;
        }
        setHasOptionsMenu(true);
        bmp = null;
    }

    // menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.menu_delete).setVisible(false);
    }
    //picture functionality
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_take_picture){

            FileUtils.getPublicAlbumStorageDir(MainActivity.IMAGE_FOLDER);
            Intent takePicutre = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //set result
            Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, takePicutre);
            startActivityForResult(takePicutre, 2);

        }else{

            //create object - safe to device
            EditText editTextTitle = Objects.requireNonNull(getActivity()).findViewById(R.id.editText_title);
            EditText editTextDescription = getActivity().findViewById(R.id.editText_description);

            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();

            if(title.matches("") || description.matches("")){
                //toast must fill in form
                Log.i(TAG, "onOptionsItemSelected: empty");
                Toast.makeText(getContext(),"Please fill out both title and description" +
                        "",Toast.LENGTH_SHORT).show();

            }else{

                //checks if external memory is mounted
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state) && bmp != null) {

                    File folder = FileUtils.getPublicAlbumStorageDir(MainActivity.IMAGE_FOLDER);
                    File file = new File(folder, getUniqueString() +".png");
                    Uri uri = Uri.fromFile(file);
                    SavedLocation savedLocation = new SavedLocation(title,
                            description,
                            uri.toString(),
                            sentLat,
                            sentLong);

                    FileUtils.SaveSerializable(MainActivity.ALL_LOCATIONS_FILE_NAME,
                            getContext(), savedLocation);
                    //save image to external storage
                    FileUtils.saveBitmapToExternalStorage(bmp, file);

                    formCallback.addLocation();
                }else{
                    Toast.makeText(getContext(),"Please be sure to include a picture" +
                            "",Toast.LENGTH_SHORT).show();
                }
            }
        }
        return true;
    }
    //appends unique value to end of the myimage string
    private String getUniqueString(){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        return format.format(today);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView iv = Objects.requireNonNull(getActivity()).findViewById(R.id.imageView_picture);
        if (requestCode == 2) {
            if(resultCode == -1){
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                bmp = photo;
                iv.setImageBitmap(photo);
            }else{
                Log.i(TAG, "onActivityResult: cancelled");
            }
        }
    }
    public interface FormCallback {
        void addLocation();
    }
}
