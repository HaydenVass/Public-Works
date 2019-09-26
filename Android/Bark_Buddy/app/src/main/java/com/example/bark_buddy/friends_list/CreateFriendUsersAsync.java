package com.example.bark_buddy.friends_list;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.bark_buddy.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class CreateFriendUsersAsync extends AsyncTask<String, ArrayList<String>,  ArrayList<User>> {

    private final ArrayList<String> friendUUIDs;
    private final onFinishedSettingFriends listener;

    public CreateFriendUsersAsync(ArrayList<String> friendUUIDs, onFinishedSettingFriends listener) {
        this.friendUUIDs = friendUUIDs;
        this.listener = listener;
    }

    @Override
    protected ArrayList<User> doInBackground(String... strings) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference userRefs = db.collection("users");
        userRefs.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    //takes all the friend UID and checks them agains the dB
                    //takes the user document associated with that user and converts the document data
                    //to java obj
                    ArrayList<User> allFriends = new ArrayList<>();
                    for(QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())){
                        String id = Objects.requireNonNull(document.get("uid")).toString();
                        if(friendUUIDs.contains(id)){
                            //create friends OBJ
                            String name = Objects.requireNonNull(document.get("dog name")).toString();
                            String email = "na";
                            String password = "na";
                            String description = Objects.requireNonNull(document.get("description")).toString();
                            String weightRange = Objects.requireNonNull(document.get("weight range")).toString();
                            String energyLevel = Objects.requireNonNull(document.get("energy level")).toString();
                            String dogName = Objects.requireNonNull(document.get("dog name")).toString();
                            String dogbreed = Objects.requireNonNull(document.get("breed")).toString();
                            String triggers = Objects.requireNonNull(document.get("triggers")).toString();
                            ArrayList<String> friends = new ArrayList<>();
                            String userID = Objects.requireNonNull(document.get("uid")).toString();
                            byte[] vacReport = new byte[]{};
                            Blob profImg = ((Blob) document.get("profImgBytes"));
                            byte[] profImgBytes = Objects.requireNonNull(profImg).toBytes();

                            User friend = new User(name, email, password, description, weightRange,
                                    energyLevel, dogName, dogbreed, triggers, friends, userID, vacReport,
                                    profImgBytes);

                            allFriends.add(friend);

                        }
                    }
                    onPostExecute(allFriends);
                }
            }
        });

        return new ArrayList<>();
    }

    //passes back to the friends list fragment
    @Override
    protected void onPostExecute(ArrayList<User> users) {
        listener.dbFriendsCreated(users);
    }

    public interface onFinishedSettingFriends{
        void dbFriendsCreated(ArrayList<User> friendObjs);
    }
}
