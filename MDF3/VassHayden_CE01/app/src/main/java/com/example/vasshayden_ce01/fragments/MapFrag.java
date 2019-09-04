package com.example.vasshayden_ce01.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vasshayden_ce01.MainActivity;
import com.example.vasshayden_ce01.R;
import com.example.vasshayden_ce01.activities.DetailsActivity;
import com.example.vasshayden_ce01.activities.FormActivity;
import com.example.vasshayden_ce01.objects.SavedLocation;
import com.example.vasshayden_ce01.utils.FileUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.Objects;

public class MapFrag extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener, LocationListener {

    public static final String TAG = "today";
    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private Location lastKnown = null;
    private boolean shouldZoom = false;

    public static MapFrag newInstance(ArrayList<SavedLocation> savedLocations, boolean shouldZoom) {
        Bundle args = new Bundle();
        args.putBoolean("SHOULDZOOM", shouldZoom);
        args.putSerializable("SAVEDLOCATIONS", savedLocations);
        MapFrag fragment = new MapFrag();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        mLocationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
        shouldZoom = Objects.requireNonNull(getArguments()).getBoolean("SHOULDZOOM");

        //location permissions
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){

            if (mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) == null){
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, this);
            }else{
                lastKnown = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                zoomInCamera(lastKnown.getLatitude(), lastKnown.getLongitude());
                mLocationManager.removeUpdates(this);
                getMapAsync(this);
            }

            // floating action button
            setupFAB();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                             MainActivity.REQUEST_LOCATION_PERMISSION);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MainActivity.REQUEST_LOCATION_PERMISSION) {
            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                mLocationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, this);
                setupFAB();
                getMapAsync(this);
            }else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MainActivity.REQUEST_LOCATION_PERMISSION);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //binds interfaces
        mMap = googleMap;
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapLongClickListener(lc);
        plotPoints();
        if(lastKnown != null){
            zoomInCamera(lastKnown.getLatitude(), lastKnown.getLongitude());
        }
    }

    //on click for long map tap
    private final GoogleMap.OnMapLongClickListener lc = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            Intent formIntent = new Intent(getContext(), FormActivity.class);
            formIntent.putExtra("LatLong", latLng);
            startActivityForResult(formIntent, 3);
        }
    };


    private void plotPoints(){
        //takes all saved points and puts them back on the map when the user navigates back from
        //adding a point or deleting a point
        if (mMap == null){
            return;
        }
        mMap.clear();
        ArrayList<SavedLocation> allLocations = FileUtils.LoadSerializable(
                MainActivity.ALL_LOCATIONS_FILE_NAME, Objects.requireNonNull(getContext()));

        for (SavedLocation s : allLocations) {
            MarkerOptions options = new MarkerOptions();
            options.title(s.getName());
            options.snippet(s.getDescription());

            LatLng cl = new LatLng(s.getLatitude(), s.getLongitutde());
            options.position(cl);

            Marker ma = mMap.addMarker(options);
            ma.setTag(s.getUri());
        }
    }
    
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View contents = LayoutInflater.from(getActivity()).inflate(R.layout.info_window, null);
        ((TextView)contents.findViewById(R.id.title)).setText(marker.getTitle());
        ((ImageView)contents.findViewById(R.id.thumbnailimg)).setImageURI(Uri.parse(Objects.requireNonNull(marker.getTag()).toString()));
        return contents;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //send tag which is image URI as intent extras
        if (marker.getTag() != null){
            Intent detailsIntent = new Intent(getContext(), DetailsActivity.class);
            detailsIntent.putExtra("tag", marker.getTag().toString());
            startActivity(detailsIntent);
        }
    }

    //location listener methods
    @Override
    public void onLocationChanged(Location location) {
        zoomInCamera(location.getLatitude(), location.getLongitude());
        mLocationManager.removeUpdates(this);

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                lastKnown = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

    }

    private void zoomInCamera(double latitutde, double longitude){
        //used to zoom in on users current location
        if(mMap == null){
            return;
        }
        LatLng currentLocal = new LatLng(latitutde, longitude);
        CameraUpdate cameraMovement = CameraUpdateFactory.newLatLngZoom(currentLocal, 16);

        if (shouldZoom){
            mMap.animateCamera(cameraMovement);
        }else{
            mMap.moveCamera(cameraMovement);
        }

    }

    private void setupFAB(){
        // floating action button
        FloatingActionButton fab = Objects.requireNonNull(getActivity()).findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formIntent = new Intent(getContext(),
                        FormActivity.class);
                LatLng latLng = new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude());
                formIntent.putExtra("LatLong", latLng);
                startActivity(formIntent);
            }
        });
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }
    @Override
    public void onProviderEnabled(String provider) { }
    @Override
    public void onProviderDisabled(String provider) { }


}

