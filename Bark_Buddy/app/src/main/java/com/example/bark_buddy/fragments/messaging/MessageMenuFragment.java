package com.example.bark_buddy.fragments.messaging;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;
import com.example.bark_buddy.friends_list.CreateFriendUsersAsync;
import com.example.bark_buddy.friends_list.FriendsAdapter;
import com.example.bark_buddy.friends_list.GetIDAsync;
import com.example.bark_buddy.objects.User;

import java.util.ArrayList;
import java.util.Objects;

public class MessageMenuFragment extends Fragment implements GetIDAsync.onFinishedGettingFriends,
        CreateFriendUsersAsync.onFinishedSettingFriends  {

    private ListView lv;
    private ArrayList<User> messageID;
    private GetIDAsync getIDAsync;
    private CreateFriendUsersAsync cfua;
    private static final String TAG = "today";


    public static MessageMenuFragment newInstance() {
        Bundle args = new Bundle();
        MessageMenuFragment fragment = new MessageMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        getIDAsync.cancel(true);
        if(cfua != null){
            cfua.cancel(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.message_menu_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = Objects.requireNonNull(getActivity()).findViewById(R.id.listView_active_messages);
        messageID = new ArrayList<>();
        //start async task
        getIDAsync = new GetIDAsync(this, "activeMessages");
        getIDAsync.execute();

        //list view on click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ( lv != null && messageID.size() != 0){
                    //pass user into specific chat
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.message_frag_container,
                            MessagingFragment.newInstance(messageID.get(position).getUserID())).addToBackStack(null).commit();
                }
            }
        });

    }

    @Override
    public void dbFriends(ArrayList<String> friendIds) {
        //set adapter to list view from ids pulled form async task
        if(friendIds.size() != 0){
            cfua = new CreateFriendUsersAsync(friendIds, this);
            cfua.execute();
        }
    }

    @Override
    public void dbFriendsCreated(ArrayList<User> friendObjs) {
        if(friendObjs.size() != 0){
            messageID = friendObjs;
            if(lv != null){
                //create adapter - set to list view
                FriendsAdapter friendsAdapter = new FriendsAdapter(getContext(), friendObjs);
                lv.setAdapter(friendsAdapter);
            }else{
                Log.i(TAG, "dbFriendsCreated: m grid view is null");
            }
        }
    }
}
