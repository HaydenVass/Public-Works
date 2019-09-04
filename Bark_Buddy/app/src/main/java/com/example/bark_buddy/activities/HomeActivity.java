package com.example.bark_buddy.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.bark_buddy.MainActivity;
import com.example.bark_buddy.R;
import com.example.bark_buddy.fragments.events.create_event.CreateEvent;
import com.example.bark_buddy.fragments.events.create_event.CreateEventP2;
import com.example.bark_buddy.fragments.events.host_events.HostingEventsFragment;
import com.example.bark_buddy.fragments.home_screen.HomeGridFragment;
import com.example.bark_buddy.fragments.profile.ProfileFragment;
import com.example.bark_buddy.friends_list.FriendsListFragment;
import com.example.bark_buddy.objects.Event;
import com.example.bark_buddy.objects.User;
import com.example.bark_buddy.utils.DBUtils;
import com.example.bark_buddy.utils.DateTimeUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements CreateEvent.CreateNewEvent,
        CreateEventP2.CreateNewEventP2 {
    private static final String TAG = "today";
    private Event holderEvent;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    //listener for bottom nav
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_friends:
                    //friends page
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                            FriendsListFragment.newInstance()).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_dashboard:
                    //home - all the near by events
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                            HomeGridFragment.newInstance()).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_profile:
                    //profile
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                            ProfileFragment.newInstance(mAuth.getUid(), true)).
                            addToBackStack(null).commit();
                    return true;
                case R.id.navigation_events:
                    //events
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                            HostingEventsFragment.newInstance()).addToBackStack(null).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        holderEvent = new Event();
        //LocationManager mLocationManager = (LocationManager) Objects.requireNonNull(getSystemService(Context.LOCATION_SERVICE));
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //hide bottom nav until inital events have been loaded
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //inital fragment swap to the home screen
        getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_container,
                HomeGridFragment.newInstance()).commit();


    }


    //interface from new event frag
    @Override
    public void newEvent(Event newEvent) {
        //set value to holder event
        holderEvent = newEvent;

    }

    @Override
    public void newEventP2(String description, String triggers) {
        //finish construction holder event
        holderEvent.setDescription(description);
        holderEvent.setTriggers(triggers);
        holderEvent.setHostID(DBUtils.getCurrentUser().getUid());
        holderEvent.setId(holderEvent.getHost() + holderEvent.getLocation() + DateTimeUtil.getCurrentTime());
        holderEvent.setPeopleAttending(new ArrayList<User>());

        //map out object in db Utils
        Map<String, Object> event = DBUtils.setEventMap(holderEvent);

        //adds user details to the database - if it was successful then the user is moved to the actual application.
        db.collection("events").document(holderEvent.getId()).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "onSuccess: event should be posted");
                Toast.makeText(HomeActivity.this,"event Posted.",Toast.LENGTH_SHORT).show();

                // add event to users profile
                DBUtils.addEventToUserDB(holderEvent.getId());
                //rotate out fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        HostingEventsFragment.newInstance()).commit();

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "onFailure: failure some where");
                    }
        });

    }

    //top menu button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        //hide logout button
        menu.findItem(R.id.action_logout).setVisible(false);


        return super.onCreateOptionsMenu(menu);
    }

    //intent to start messaging activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_message){
            Intent i= new Intent (HomeActivity.this, ChatActifity.class);
            startActivity(i);
        }
        //log out intent to close this intent and start a new one
        if(item.getItemId() == R.id.action_logout){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
