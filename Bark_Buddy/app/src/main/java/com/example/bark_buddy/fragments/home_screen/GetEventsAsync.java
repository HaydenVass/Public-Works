package com.example.bark_buddy.fragments.home_screen;

import android.location.Location;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.bark_buddy.objects.Event;
import com.example.bark_buddy.utils.LocationUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

class GetEventsAsync extends AsyncTask<String, String, String> {

    private ArrayList<Event> pulledEvents = new ArrayList<>();
    final private onFinishedGettingDBEvents mFinishedInterface;
    private final Location lastKnown;
    private final int mileRadius;

    public GetEventsAsync(onFinishedGettingDBEvents fi, Location userLocation, int desiredMileRadius) {
        mFinishedInterface = fi;
        lastKnown = userLocation;
        mileRadius = desiredMileRadius;
    }


    @Override
    protected String doInBackground(String... strings) {
        final ArrayList<Event> allEvents = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventRefs = db.collection("events");
        eventRefs.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    //loop through all the events
                    for(QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())){

                        //get mapped data from database
                        String triggers = Objects.requireNonNull(document.get("Triggers")).toString();
                        String date = Objects.requireNonNull(document.get("date")).toString();
                        String description = Objects.requireNonNull(document.get("description")).toString();
                        String endtime = Objects.requireNonNull(document.get("endtime")).toString();
                        String startTime = Objects.requireNonNull(document.get("startTime")).toString();
                        String energyLevel = Objects.requireNonNull(document.get("energyLevel")).toString();
                        GeoPoint eventGeoPoint = ((GeoPoint) document.get("eventGeoPoint"));
                        String eventId = Objects.requireNonNull(document.get("eventId")).toString();
                        String host = Objects.requireNonNull(document.get("host")).toString();
                        String hostID = Objects.requireNonNull(document.get("hostId")).toString();
                        Blob imgBytesBlob = ((Blob) document.get("imgBytes"));
                        String location = Objects.requireNonNull(document.get("location")).toString();
                        String weightRange = Objects.requireNonNull(document.get("weightRange")).toString();

                        //start filtering
                        //filter on distance with miles selected from user prefs.

                        if(LocationUtils.checkDistance(mileRadius, lastKnown.getLatitude(),
                                lastKnown.getLongitude(), Objects.requireNonNull(eventGeoPoint).getLatitude(),
                                eventGeoPoint.getLongitude())){
                            //check weight range


                            allEvents.add(new Event(eventId, Objects.requireNonNull(imgBytesBlob).toBytes(), energyLevel, weightRange,
                                    host, date, startTime, endtime, location, description, triggers, eventGeoPoint,
                                    hostID));

                        }
                    }
                    pulledEvents = allEvents;
                    onPostExecute("");

                }
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        mFinishedInterface.dbEvents(pulledEvents);
    }

    //protocol to send array of events back
    public interface onFinishedGettingDBEvents{
        void dbEvents(ArrayList<Event> dbEvents);
    }
}
