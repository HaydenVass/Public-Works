package com.example.bark_buddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bark_buddy.R;
import com.example.bark_buddy.fragments.messaging.MessageMenuFragment;
import com.example.bark_buddy.fragments.messaging.MessagingFragment;

public class ChatActifity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_actifity);

        //there will be extras of a target UID if the user pressed message from another users profile
        Bundle extras = getIntent().getExtras();

        //checks if theres extra, if not it will open the list of active messages.
        //if there is an extra - it will go straight to the message
        if (extras != null) {
            String targetUUID = extras.getString("targetUUID");
            getSupportFragmentManager().beginTransaction().replace(R.id.message_frag_container,
                    MessagingFragment.newInstance(targetUUID)).commit();

        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.message_frag_container,
                    MessageMenuFragment.newInstance()).commit();
        }
    }
}
