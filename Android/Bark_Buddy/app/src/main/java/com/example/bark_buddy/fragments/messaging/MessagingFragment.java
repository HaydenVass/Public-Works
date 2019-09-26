package com.example.bark_buddy.fragments.messaging;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;
import com.example.bark_buddy.adapters.MessageAdapter;
import com.example.bark_buddy.objects.ChatChannel;
import com.example.bark_buddy.objects.Message;
import com.example.bark_buddy.utils.DBUtils;
import com.example.bark_buddy.utils.DateTimeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Objects;

public class MessagingFragment extends Fragment implements GetMessagesAsync.onFinishedGettingChat {

    private static final String  SENT_UI_KEY = "sentUidKey";
    private ListView chatsMessages;
    private EditText messageEditText;
    private FirebaseFirestore db;
    private ChatChannel currentChannel;
    private ImageView sendButton;
    private String currentUserUid;
    private String otherUserUid;
    private ListenerRegistration messageListener;
    private GetMessagesAsync getMessagesAsync;


    public static MessagingFragment newInstance(String selectedUUID) {
        Bundle args = new Bundle();
        args.putString(SENT_UI_KEY, selectedUUID);
        MessagingFragment fragment = new MessagingFragment();
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
        return inflater.inflate(R.layout.message_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        messageListener = db.collection("chats").document(currentUserUid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot,
                                @javax.annotation.Nullable FirebaseFirestoreException e) {

                if(e != null){
                    e.printStackTrace();
                    return;
                }
                getMessages(currentUserUid);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        messageListener.remove();
    }

    @Override
    public void onPause() {
        if(getMessagesAsync != null){
            getMessagesAsync.cancel(true);
        }
        super.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentChannel = new ChatChannel();
        db = FirebaseFirestore.getInstance();
        currentUserUid = DBUtils.getCurrentUser().getUid();
        otherUserUid = Objects.requireNonNull(getArguments()).getString(SENT_UI_KEY);
        final ImageView chattingWithImg = Objects.requireNonNull(getActivity()).findViewById(R.id.imageView_chatwithImg);
        final TextView chattingWithWho = getActivity().findViewById(R.id.textView_chatwithWho);

        sendButton = Objects.requireNonNull(getActivity()).findViewById(R.id.imageView_send);
        messageEditText = getActivity().findViewById(R.id.editText_message);
        chatsMessages = getActivity().findViewById(R.id.listView_chats);


        //document reference to get the image and name of the person the user is currently chatting with
        DocumentReference documentReference = db.collection("users").document(otherUserUid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Blob blob = documentSnapshot.getBlob("profImgBytes");
                    Bitmap bmp = BitmapFactory.decodeByteArray(Objects.requireNonNull(blob).toBytes(), 0, blob.toBytes().length);
                    chattingWithImg.setImageBitmap(bmp);

                    chattingWithWho.setText(Objects.requireNonNull(documentSnapshot.get("dog name")).toString());
                }
            }
        });

    }

    //adds the message to both the user and the recipient -- this is so the recepient has the messages
    //when it gets loaded in from their document.
    private void sendMessage(String currentUserId, String recieverID, String message, String timeStamp, final EditText editText){
        Message message1A = new Message(currentUserId, recieverID, message, timeStamp);
        currentChannel.getMessages().add(message1A);
        addMessageToDB(currentUserId, editText, currentChannel, true);
        addMessageToDB(recieverID, editText, currentChannel, false);
        DBUtils.addActiveMessageToReciever(recieverID);

    }

    private void addMessageToDB(final String senderId, final EditText editText, ChatChannel chatChannel, final boolean shouldToast){
        db.collection("chats").document(senderId).set(chatChannel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(shouldToast){
                    if(task.isSuccessful()){
                        //toast to confirm message sent
                        Toast.makeText(getContext(), getResources().getString(R.string.msgSent),
                                Toast.LENGTH_SHORT).show();
                        //dismiss key board
                        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity())
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        //clear edit text
                        editText.setText("");
                        getMessages(senderId);
                    }else{
                        Toast.makeText(getContext(), getResources().getString(R.string.msgNotSent),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //pulls messages from db
    private void getMessages(String currentUserUid){
        getMessagesAsync = new GetMessagesAsync(this, currentUserUid);
        getMessagesAsync.execute();
    }


    private void setAdapter(ArrayList<Message> messages){
        //set message adapter
        MessageAdapter messageAdapter = new MessageAdapter(getContext(),
                messages, currentUserUid);
        chatsMessages.setAdapter(messageAdapter);

    }


    @Override
    public void dbChatMsgs(ChatChannel chatChannel) {
        if(chatChannel != null){
            currentChannel = chatChannel;
            //on click in call back - so user cant jam up que
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMessage(currentUserUid, otherUserUid, messageEditText.getText().toString(),
                            DateTimeUtil.getCurrentTime(), messageEditText);
                }
            });
            
            if(chatChannel.getMessages().size() != 0){
                //check messages to make sure user is only getting messages between correct users
                //since the chat channel contains chats for all interactions
                //this is also attached to the on document changed listener so it will update when either user
                //send a message
                ArrayList<Message> targetMessages = new ArrayList<>();
                for (Message m : chatChannel.getMessages()) {
                    if(m.getRecieverId().equals(otherUserUid) && m.getSenderId().equals(currentUserUid) ||
                    m.getRecieverId().equals(currentUserUid) && m.getSenderId().equals(otherUserUid)){
                        targetMessages.add(m);
                    }
                }
                setAdapter(targetMessages);
                //chatsMessages.setSelection(targetMessages.size() - 1);


            }
        }
    }

}
