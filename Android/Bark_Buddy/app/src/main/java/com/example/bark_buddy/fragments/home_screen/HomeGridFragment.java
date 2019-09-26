package com.example.bark_buddy.fragments.home_screen;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;
import com.example.bark_buddy.adapters.EventAdapter;
import com.example.bark_buddy.fragments.events.view_events.ViewEvent;
import com.example.bark_buddy.objects.Event;
import com.example.bark_buddy.utils.DBUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Objects;

public class HomeGridFragment extends Fragment implements LocationListener,
        GetEventsAsync.onFinishedGettingDBEvents{


    private LocationManager mLocationManager;
    private Location lastKnown;
    private GridView mGridview;
    private ArrayList<Event>displayedEvents;
    private GetEventsAsync getEventsAsync;
    private SharedPreferences sharedPref ;


    public HomeGridFragment() {
    }

    public static HomeGridFragment newInstance() {
        Bundle args = new Bundle();
        HomeGridFragment fragment = new HomeGridFragment();
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
        super.onActivityCreated(savedInstanceState);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        mGridview = Objects.requireNonNull(getActivity()).findViewById(R.id.event_home_grid);
        displayedEvents = new ArrayList<>();

        //grid on item click
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle eventData = DBUtils.getEventDataNotSeralizable(displayedEvents.get(position));

                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().
                        replace(R.id.home_fragment_container, ViewEvent.newInstance(eventData)).
                        addToBackStack(null).commit();
            }
        });

        //sets location manager to get current location
        mLocationManager = (LocationManager) Objects.requireNonNull(getActivity().getSystemService(Context.LOCATION_SERVICE));

        //checks if location manager is already running
        //if its not, it will go ahead and fire an instance of the location manager up
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) == null) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                    0, this);
        } else {
            lastKnown = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mLocationManager.removeUpdates(this);
        }


        //set location
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser cU = DBUtils.getCurrentUser();
        DocumentReference currentUserDoc = db.collection("users").document(cU.getUid());
        GeoPoint currentPos = null;
        if(lastKnown!= null){
            currentPos = new GeoPoint(lastKnown.getLatitude(), lastKnown.getLongitude());
        }
        currentUserDoc.update("location", currentPos);

        //swap fragment for filter prefs
        ImageView filterImg = getActivity().findViewById(R.id.imageView_filterImg);
        filterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().
                        replace(R.id.home_fragment_container,
                        FilterFragment.newInstance()).addToBackStack(null).commit();
            }
        });


        //pulls events off location async - returns the list of events
        // call back at the bottom -- "dbEvents"
        getEventsAsync = new GetEventsAsync(this, lastKnown,
                Integer.valueOf(Objects.requireNonNull(sharedPref.
                        getString(FilterFragment.DISTANCE_KEY, "25"))));
        getEventsAsync.execute("");
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getEventsAsync != null){
            getEventsAsync.cancel(true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //if the location changes drastically then the location manager will upate the current users local
        lastKnown = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }


    //primary filtering for the events
    //will filter off the user prefs set in the filter
    //puts events that match both filters at the top
    //then puts events that match at least one filter below that
    //distance is filtered in the back ground while the events get filtered from triggers
    //below
    @Override
    public void dbEvents(ArrayList<Event> dbEvents) {
        if(mGridview != null){
            String filterWeightRange = sharedPref.getString(FilterFragment.WEIGHT_KEY, "Any");
            String filterEnergy = sharedPref.getString(FilterFragment.ENERGY_KEY, "Any");

            for (Event event : dbEvents) {
                //Checks if both filters match
                if(event.getWeightRange().equals(filterWeightRange) &&
                        event.getEnergyLevel().equals(filterEnergy)){
                    displayedEvents.add(event);
                }
            }
            //check if at least one matches
            for (Event event : dbEvents) {
                if(event.getWeightRange().equals(filterWeightRange) ||
                        event.getEnergyLevel().equals(filterEnergy)){
                    if(!displayedEvents.contains(event)){
                        displayedEvents.add(event);
                    }
                }
            }


            DocumentReference documentReference = DBUtils.getCurrentUserDoc();
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        //get current user triggers
                        String triggers = Objects.requireNonNull(documentSnapshot.get("triggers")).toString();
                        String[] userTriggers = triggers.split(" ");

                        //take strings in this split list and convert them all to lower case.
                        ArrayList<String> loweredUserTriggers = stringArrayToLowerList(userTriggers);
                        ArrayList<Event> eventsToRemove = new ArrayList<>();
                        //check those against events that match filter settings
                        for (Event event : displayedEvents) {
                            String[] eventTriggers = event.getTriggers().split(" ");
                            ArrayList<String> loweredEventTriggers = stringArrayToLowerList(eventTriggers);

                            //compare the amount of similarities between the two list.
                            loweredEventTriggers.retainAll(loweredUserTriggers);

                            //cant modify in loop - so added in tertiary list
                            //change this number to increase or decrease the amount of similar triggers needed
                            //to filter out the event
                            if(loweredEventTriggers.size() > 1){
                                eventsToRemove.add(event);
                            }
                        }

                        //remove own events from home screen
                        displayedEvents.removeAll(eventsToRemove);
                        ArrayList<Event> ownEvents = new ArrayList<>();
                        for (Event event : displayedEvents) {
                            if(event.getHostID().equals(DBUtils.getCurrentUser().getUid())){
                                ownEvents.add(event);
                            }
                        }
                        displayedEvents.removeAll(ownEvents);

                        EventAdapter hsa = new EventAdapter(getContext(), displayedEvents);
                        mGridview.setAdapter(hsa);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }


    private ArrayList<String> stringArrayToLowerList(String[] strings){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (String str : strings) {
            //checks if the strings last character is a , or .
            //if it does it then removes it -- used for filtereing
            if(str.endsWith(",") || str.endsWith(".")){
                str = str.substring(0, str.length() - 1);
            }
            stringArrayList.add(str.toLowerCase());
        }
        return stringArrayList;
    }



}
