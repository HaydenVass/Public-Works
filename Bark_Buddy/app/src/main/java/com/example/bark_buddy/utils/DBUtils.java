package com.example.bark_buddy.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.example.bark_buddy.R;
import com.example.bark_buddy.fragments.events.host_events.HostingEventsFragment;
import com.example.bark_buddy.objects.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DBUtils {

    public static FirebaseUser getCurrentUser(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser();
    }

    public static DocumentReference getCurrentUserDoc(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return db.collection("users").document(Objects.requireNonNull(currentUser).getUid());
    }

    private static DocumentReference getTargetUserDoc(String userId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("users").document(userId);
    }

    //friends
    private static void addFriend(String friendUserId, final Context context){
        DocumentReference currentUser = getCurrentUserDoc();
        currentUser.update("friends", FieldValue.arrayUnion(friendUserId)).
                addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"Friend Added",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void removeFriend(final String friendUserId, final Context context, final Button button){
        final DocumentReference currentUser = getCurrentUserDoc();
        new AlertDialog.Builder(context)
                .setTitle("Remove Friend")
                .setMessage("Are you sure you want to remove this friend?")
                //listener for deleting user
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //delete
                        currentUser.update("friends", FieldValue.arrayRemove(friendUserId)).
                                //toast to make srue
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context,"Friend Removed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        button.setText("Remove Friend");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //messages
    public static void addUserToActiveMessages(String recieverUUID){
        DocumentReference currentUser = getCurrentUserDoc();
        currentUser.update("activeMessages", FieldValue.arrayUnion(recieverUUID));
    }

    //add sent message to other user
    public static void addActiveMessageToReciever(String recieverUUID){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(recieverUUID).
                update("activeMessages", FieldValue.arrayUnion(DBUtils.getCurrentUser().getUid()));
    }


    //takes the buttons from the profile fragment
    //the buttons will set and change functionality pending what the current user has
    //if the user has a selected user as a friend they will be able to remove that firned
    //reversely they will be able to add new friends
    public static void setFriendsButton( final String friendURI, final Button button, final Context context){
        DocumentReference currentUser = getCurrentUserDoc();
        currentUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    final DocumentSnapshot document = task.getResult();
                    @SuppressWarnings("unchecked") ArrayList<String> friends = (ArrayList<String>) Objects.requireNonNull(document).get("friends");
                    if(Objects.requireNonNull(friends).contains(friendURI) && button!=null){
                        button.setText("Remove Friend");
                    }else if (button != null){
                        button.setText("Add Friend");
                    }

                    Objects.requireNonNull(button).setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {
                            if(Objects.requireNonNull(button).getText().equals("Remove Friend")){
                                DBUtils.removeFriend(friendURI, context, button);
                                button.setText("Add Friend");
                            }else if(button.getText().equals("Add Friend")){
                                DBUtils.addFriend(friendURI, context);
                                button.setText("Remove Friend");
                            }
                        }
                    });
                }
            }
        });

    }

    //like the method above, this is for setting the attend button
    //the button houses functionality to either attend, remove or delete events -- pending the users
    //reltionship with the event. IE if they are hosting or just attending.
    public static void setAttendButton(final String eventID, final Button button, final Context context,
                                       final FragmentTransaction fragmentTransaction){
        final FirebaseUser currentUser = DBUtils.getCurrentUser();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        //get document ref
        DocumentReference eventDocRef = db.collection("events").document(eventID);
        eventDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    final DocumentSnapshot document = task.getResult();
                    ArrayList<String> userIDs = ((ArrayList<String>) Objects.requireNonNull(document).get("peopleAttending"));
                    //checks to see if the person accessing content is the current user or not
                    String hostID = Objects.requireNonNull(document.get("hostId")).toString();
                    if(currentUser.getUid().equals(hostID) && button !=null){
                        //if the user is the same then the event was created by them so the button changes
                        //to the delete button
                        button.setText("Delete Event");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               //allert dialgo for deleting an event
                                new AlertDialog.Builder(context)
                                        .setTitle("Delete Event")
                                        .setMessage("Are you sure you want to delete this event?")
                                        //listener for deleting user
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                db.collection("events").document(Objects.requireNonNull(document.get("eventId")).toString()).
                                                        delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        //deletes event
                                                        Toast.makeText(context, "Event Deleted", Toast.LENGTH_SHORT).show();
                                                        fragmentTransaction.replace(R.id.home_fragment_container,
                                                                HostingEventsFragment.newInstance()).commit();
                                                    }
                                                });
                                            }
                                        })
                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setNegativeButton(android.R.string.no, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        });
                    }else{
                        //if its not the current user then the code checks to see if they are already attending
                        //the event if or not and changes the button accordingly
                        if(Objects.requireNonNull(userIDs).contains(currentUser.getUid()) && button != null){
                            button.setText("Not Attending");
                        }else if (button != null){
                            button.setText("Attend");
                        }

                        //on click for the button -- changes functionality pending what the text is.
                        // if the user is already attending then they can remove them self from the event
                        //other wise they can say they will be attending.
                        Objects.requireNonNull(button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(button.getText().equals("Attend")){
                                    //add event to user in DB
                                    DBUtils.addAttendingEventToUserDB(eventID);
                                    //add user to event
                                    DBUtils.addUsertoEventDB(eventID, DBUtils.getCurrentUser().getUid());
                                    //change text to button so they can remove
                                    button.setText("Not Attending");
                                }else{
                                    //allert dialog for un attending an event
                                    new AlertDialog.Builder(context)
                                            .setTitle("Stop Attending")
                                            .setMessage("Are you sure you want to not attend this event?")
                                            //listener for deleting user
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //remove event from user DB
                                                    DBUtils.removeAttendingEventToUserDB(eventID);
                                                    //remove from event
                                                    DBUtils.removeUsertoEventDB(eventID, DBUtils.getCurrentUser().getUid());
                                                    //change text to add event to DB on second press
                                                    button.setText("Attend");
                                                }
                                            })
                                            // A null listener allows the button to dismiss the dialog and take no further action.
                                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    button.setText("Not Attending");
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }


    //events - name describe functionality
    public static void addEventToUserDB(String eventID){
        DocumentReference currentDoc = getCurrentUserDoc();
        currentDoc.update("hostingEvents", FieldValue.arrayUnion(eventID));
    }

    private static void addAttendingEventToUserDB(String eventID){
        DocumentReference currentDoc = getCurrentUserDoc();
        currentDoc.update("attendingEvents", FieldValue.arrayUnion(eventID));

    }
    private static void removeAttendingEventToUserDB(String eventID){
        DocumentReference currentDoc = getCurrentUserDoc();
        currentDoc.update("attendingEvents", FieldValue.arrayRemove(eventID));

    }

    private static void addUsertoEventDB(String eventID, String userId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events").document(eventID).
        update("peopleAttending", FieldValue.arrayUnion(userId));

    }
    private static void removeUsertoEventDB(String eventID, String userId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events").document(eventID).
                update("peopleAttending", FieldValue.arrayRemove(userId));
    }



    public static void setTextViewWithUserData( final TextView name, final String ops){
        final DocumentReference documentReference = getCurrentUserDoc();
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    String tea = Objects.requireNonNull(Objects.requireNonNull(document).get("dog name")).toString();
                    if(name != null){
                        name.setText(tea);
                    }
                }else{
                    name.setText(ops);
                }
            }
        });
    }

    public static void setImageViewFromDB(final ImageView imageView, final Bitmap error){
        final DocumentReference documentReference = getCurrentUserDoc();
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Blob profileImgBlob = (Blob) Objects.requireNonNull(document).get("profImgBytes");
                    Bitmap bmp = BitmapFactory.decodeByteArray(Objects.requireNonNull(profileImgBlob).toBytes(), 0, profileImgBlob.toBytes().length);
                    if(imageView != null){
                        imageView.setImageBitmap(bmp);
                    }
                }else{
                    //task was not succesfull
                    //sets an error image if the image was not recoverd
                    imageView.setImageBitmap(error);
                }
            }
        });
    }

    public static void setTargetUserImg(String userId, final ImageView imageView, final Bitmap bitmap){
        final DocumentReference documentReference = getTargetUserDoc(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Blob profileImgBlob = (Blob) Objects.requireNonNull(document).get("profImgBytes");
                    Bitmap bmp = BitmapFactory.decodeByteArray(Objects.requireNonNull(profileImgBlob).toBytes(), 0, profileImgBlob.toBytes().length);
                    if(imageView != null){
                        imageView.setImageBitmap(bmp);
                    }
                }else{
                    //task was not succesfull
                    imageView.setImageBitmap(bitmap);
                }
            }
        });

    }


    public static Map<String, Object> setEventMap(Event holderEvent){
        Map<String, Object> event = new HashMap<>();
        event.put("eventId", holderEvent.getId());
        event.put("imgBytes", Blob.fromBytes(holderEvent.getImgBytes()));
        event.put("energyLevel", holderEvent.getEnergyLevel());
        event.put("weightRange", holderEvent.getWeightRange());
        event.put("host", holderEvent.getHost());
        event.put("date", holderEvent.getDate());
        event.put("startTime", holderEvent.getStartTime());
        event.put("endtime", holderEvent.getEndTime());
        event.put("location", holderEvent.getLocation());
        event.put("description", holderEvent.getDescription());
        event.put("eventGeoPoint", holderEvent.getGeoPointLocation());
        event.put("hostId", holderEvent.getHostID());
        event.put("Triggers", holderEvent.getTriggers());
        event.put("peopleAttending", holderEvent.getPeopleAttending());
        return event;
    }


    public static Bundle getEventDataNotSeralizable (Event holderEvent){
        Bundle event = new Bundle();
        event.putString("eventId", holderEvent.getId());
        event.putByteArray("imgBytes", holderEvent.getImgBytes());
        event.putString("energyLevel", holderEvent.getEnergyLevel());
        event.putString("weightRange", holderEvent.getWeightRange());
        event.putString("host", holderEvent.getHost());
        event.putString("date", holderEvent.getDate());
        event.putString("startTime", holderEvent.getStartTime());
        event.putString("endtime", holderEvent.getEndTime());
        event.putString("location", holderEvent.getLocation());
        event.putString("description", holderEvent.getDescription());
        event.putString("hostId", holderEvent.getHostID());
        event.putString("Triggers", holderEvent.getTriggers());
        //event.("peopleAttending", holderEvent.getPeopleAttending());
        return event;
    }

}
