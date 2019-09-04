package com.example.bark_buddy.friends_list;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.bark_buddy.utils.DBUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class GetIDAsync extends AsyncTask<String, ArrayList<String>,  ArrayList<String>> {

    private final onFinishedGettingFriends listener;
    private final String targetDocument;



    public GetIDAsync(onFinishedGettingFriends listener, String targetDocument) {
        this.listener = listener;
        this.targetDocument = targetDocument;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        DocumentReference currentUser = DBUtils.getCurrentUserDoc();
        currentUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    //gets snapshot of current users friends list and returns the ID of all the friends
                    //which will be used in a second async task that will convert the IDS to friend OBJS
                    final DocumentSnapshot document = task.getResult();
                    @SuppressWarnings("unchecked") ArrayList<String> friends = (ArrayList<String>) Objects.requireNonNull(document).get(targetDocument);
                    onPostExecute(friends);

                }
            }
        });

        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        listener.dbFriends(strings);
    }

    public interface onFinishedGettingFriends{
        void dbFriends(ArrayList<String> friendIds);
    }
}
