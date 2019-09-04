package com.example.bark_buddy.fragments.events.host_events;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.bark_buddy.utils.DBUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("unchecked")
class GetEventIdsAsync extends AsyncTask<String, String, String>{

    private final int switchIntention ;
    private ArrayList<String> holdingEvents;
    private final onFinishedGettingDBHostingString listener;

    public GetEventIdsAsync(int switchIntention, onFinishedGettingDBHostingString listener) {
        this.switchIntention = switchIntention;
        this.listener = listener;
    }

    //usesd to get all IDs from the user then takes those IDS and
    //runs a query against the database for the particular objects
    //inwhich it takes and returns the java object

    @Override
    protected String doInBackground(String... strings) {
        holdingEvents = new ArrayList<>();
        final DocumentReference currentUserDoc = DBUtils.getCurrentUserDoc();
        currentUserDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    switch (switchIntention){
                        //case checks if the user is attending or hosting
                        case 0:
                            //get all hosting event IDS
                            //noinspection unchecked
                            holdingEvents = (ArrayList<String>) Objects.requireNonNull(document).get("hostingEvents");
                            onPostExecute("");
                            break;
                        case 1:
                            //get all hosting event IDS
                            //noinspection unchecked
                            holdingEvents = (ArrayList<String>) Objects.requireNonNull(document).get("attendingEvents");
                            onPostExecute("");
                            break;
                    }

                }
            }
        });


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (holdingEvents != null){
            listener.dbHostingEventStrings(holdingEvents);
        }else{
            holdingEvents = new ArrayList<>();
            listener.dbHostingEventStrings(holdingEvents);

        }
    }

    public interface onFinishedGettingDBHostingString{
        void dbHostingEventStrings(ArrayList<String> dbEvents);
    }
}
