package com.example.bark_buddy.fragments.messaging;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.bark_buddy.objects.ChatChannel;
import com.example.bark_buddy.objects.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

class GetMessagesAsync extends AsyncTask<String, ChatChannel,  ChatChannel> {
    private final onFinishedGettingChat listener;
    private final String currentUserId;

    public GetMessagesAsync(onFinishedGettingChat listener, String currentUserId) {
        this.listener = listener;
        this.currentUserId = currentUserId;
    }

    //checks the current user and gets the chat channel associated with them
    //the chat channel contains time stamp, both people in the chat and the message
    @Override
    protected ChatChannel doInBackground(String... strings) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("chats").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentReference = task.getResult();
                    ChatChannel chatChannel = Objects.requireNonNull(documentReference).toObject(ChatChannel.class);
                    onPostExecute(chatChannel);
                }
            }
        });
        return new ChatChannel(new ArrayList<Message>());
    }

    @Override
    protected void onPostExecute(ChatChannel chatChannel) {
        if(chatChannel != null){
            //sends the chat channel back to the MessagesActivity
            listener.dbChatMsgs(chatChannel);
        }
    }

    public interface onFinishedGettingChat{
        void dbChatMsgs(ChatChannel chatChannel);
    }
}
