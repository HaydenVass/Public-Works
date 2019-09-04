package com.example.vasshayden_ce01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.vasshayden_ce01.fragments.MapFrag;
import com.example.vasshayden_ce01.objects.SavedLocation;
import com.example.vasshayden_ce01.utils.FileUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="today";
    public static final String IMAGE_FOLDER = "googleimages";
    public static final int REQUEST_LOCATION_PERMISSION = 0x01002;

    public static final String ALL_LOCATIONS_FILE_NAME = "SAVEDLOCATIONS";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<SavedLocation> locations;
        locations = FileUtils.LoadSerializable(ALL_LOCATIONS_FILE_NAME, this);

        Intent i = getIntent();
        String shouldZoom = i.getStringExtra("shouldZoom");
        Log.i(TAG, "onCreate: " + shouldZoom);
        if(shouldZoom != null){
            loadMapFragment(locations, false);
        }else{
            if (savedInstanceState == null) {
                loadMapFragment(locations, true);
            }
        }

    }

    private void loadMapFragment(ArrayList<SavedLocation> sl, boolean shouldZoom){
        MapFrag frag = MapFrag.newInstance(sl, shouldZoom);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag,
                MapFrag.TAG).commit();
    }
}
