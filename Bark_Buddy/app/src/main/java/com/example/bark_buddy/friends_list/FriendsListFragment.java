package com.example.bark_buddy.friends_list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;
import com.example.bark_buddy.fragments.profile.ProfileFragment;
import com.example.bark_buddy.objects.User;

import java.util.ArrayList;
import java.util.Objects;


public class FriendsListFragment extends Fragment implements GetIDAsync.onFinishedGettingFriends,
CreateFriendUsersAsync.onFinishedSettingFriends{

    private static final String TAG = "today";
    private GridView mGridView;
    private ArrayList<User> friends;
    private GetIDAsync getIDAsync;
    private CreateFriendUsersAsync createFriendUsersAsync;

    public static FriendsListFragment newInstance() {
        Bundle args = new Bundle();
        FriendsListFragment fragment = new FriendsListFragment();
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
        return inflater.inflate(R.layout.friends_fragment, container, false);
    }

    @Override
    public void onPause() {
        if(getIDAsync!=null){
            getIDAsync.cancel(true);
        }
        if(createFriendUsersAsync!=null){
            createFriendUsersAsync.cancel(true);
        }
        super.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        friends = new ArrayList<>();
        mGridView = Objects.requireNonNull(getActivity()).findViewById(R.id.friends_grid_view);
        getIDAsync = new GetIDAsync(this, "friends");
        getIDAsync.execute();

        if(mGridView != null){

            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().
                            replace(R.id.home_fragment_container,
                            ProfileFragment.newInstance(friends.get(position).getUserID(),
                                    false)).addToBackStack(null).commit();
                }
            });
        }
    }

    //gets friend IDS
    @Override
    public void dbFriends(ArrayList<String> friendIds) {
        //takes IDS to start next Async task
        if(friendIds.size() != 0){
            createFriendUsersAsync = new CreateFriendUsersAsync(friendIds, this);
            createFriendUsersAsync.execute();
        }
    }

    //call back from second async task
    //takes friends OBJs and puts them in list view
    // needed OBJS so the user can check their friends profile from here
    @Override
    public void dbFriendsCreated(ArrayList<User> friendObjs) {
        if(friendObjs.size() != 0){
            friends = friendObjs;
            if(mGridView != null){
                //create adapter - set to grid view
                FriendsAdapter friendsAdapter = new FriendsAdapter(getContext(), friendObjs);
                mGridView.setAdapter(friendsAdapter);
            }else{
                Log.i(TAG, "dbFriendsCreated: m grid view is null");
            }
        }
    }
}
