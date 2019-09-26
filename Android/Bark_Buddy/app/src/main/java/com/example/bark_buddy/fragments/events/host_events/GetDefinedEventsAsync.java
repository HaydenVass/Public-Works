package com.example.bark_buddy.fragments.events.host_events;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.bark_buddy.objects.Event;
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

class GetDefinedEventsAsync extends AsyncTask<String, String, String> {

    private final ArrayList<String> passedEventIds;
    private final ArrayList<Event> allTargetedEvents = new ArrayList<>();
    final private onFinishedGettingDBHostingEvents mFinishedInterface;

    public GetDefinedEventsAsync(onFinishedGettingDBHostingEvents mFinishedInterface,
                                 ArrayList<String> hostEventIds) {
        this.mFinishedInterface = mFinishedInterface;
        passedEventIds = hostEventIds;
    }

    @Override
    protected String doInBackground(String... strings) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventRefs = db.collection("events");
        eventRefs.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    //loop through all the events
                    for(QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())){
                        String eventId = Objects.requireNonNull(document.get("eventId")).toString();
                        if(passedEventIds.contains(eventId)){
                            //get mapped data from database - add hosting events
                            String triggers = Objects.requireNonNull(document.get("Triggers")).toString();
                            String date = Objects.requireNonNull(document.get("date")).toString();
                            String description = Objects.requireNonNull(document.get("description")).toString();
                            String endtime = Objects.requireNonNull(document.get("endtime")).toString();
                            String startTime = Objects.requireNonNull(document.get("startTime")).toString();
                            String energyLevel = Objects.requireNonNull(document.get("energyLevel")).toString();
                            GeoPoint eventGeoPoint = ((GeoPoint) document.get("eventGeoPoint"));
                            String host = Objects.requireNonNull(document.get("host")).toString();
                            Blob imgBytesBlob = ((Blob) document.get("imgBytes"));
                            String location = Objects.requireNonNull(document.get("location")).toString();
                            String hostID = Objects.requireNonNull(document.get("hostId")).toString();
                            String weightRange = Objects.requireNonNull(document.get("weightRange")).toString();

                            //creates event obj
                            allTargetedEvents.add(new Event(eventId, Objects.requireNonNull(imgBytesBlob).toBytes(), energyLevel, weightRange,
                                    host, date, startTime, endtime, location, description, triggers, eventGeoPoint,
                                    hostID));

                        }

                    }
                    onPostExecute("");

                }
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(!allTargetedEvents.isEmpty()){
            //pass bacvk to Hosting events fragment
            mFinishedInterface.dbHostingEvents(allTargetedEvents);
        }
    }

    public interface onFinishedGettingDBHostingEvents{
        void dbHostingEvents(ArrayList<Event> dbEvents);
    }
}
