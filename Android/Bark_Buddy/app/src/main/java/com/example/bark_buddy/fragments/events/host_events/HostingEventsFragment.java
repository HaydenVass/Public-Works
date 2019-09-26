package com.example.bark_buddy.fragments.events.host_events;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;
import com.example.bark_buddy.adapters.EventAdapter;
import com.example.bark_buddy.fragments.events.create_event.CreateEvent;
import com.example.bark_buddy.fragments.events.view_events.ViewEvent;
import com.example.bark_buddy.objects.Event;
import com.example.bark_buddy.utils.DBUtils;

import java.util.ArrayList;
import java.util.Objects;

public class HostingEventsFragment extends Fragment implements GetEventIdsAsync.onFinishedGettingDBHostingString,
GetDefinedEventsAsync.onFinishedGettingDBHostingEvents{

    private static final String TAG = "today";
    private GridView mGridview;
    private ArrayList<Event> currentEvents;
    private GetEventIdsAsync hostIdsAsync;
    private GetDefinedEventsAsync nextTask;
    public HostingEventsFragment() { }

    public static HostingEventsFragment newInstance() {
        Bundle args = new Bundle();
        HostingEventsFragment fragment = new HostingEventsFragment();
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
        return inflater.inflate(R.layout.events_home_hosting, container, false);
    }

    //stop async task if fragment is being cycled out.
    @Override
    public void onPause() {
        super.onPause();
        if(hostIdsAsync!=null){
            hostIdsAsync.cancel(true);
        }
        if(nextTask !=null ){
            nextTask.cancel(true);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentEvents = new ArrayList<>();
        mGridview = Objects.requireNonNull(getActivity()).findViewById(R.id.hosting_gridview_attending);
        // task for getting hosting event IDs
        hostIdsAsync = new GetEventIdsAsync(1, this);
        hostIdsAsync.execute("");

        //create event
        Button createEventButton = getActivity().findViewById(R.id.button_e_home_create_event_attending);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //swap to create page
                Log.i(TAG, "onClick: ");
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().
                        replace(R.id.home_fragment_container,
                        CreateEvent.newInstance()).
                        addToBackStack(null).commit();
            }
        });
        final Button attendingBtn = getActivity().findViewById(R.id.button_user_attending_events_attending);
        final Button hostingBtn = getActivity().findViewById(R.id.user_hosting_events_attending);


        //These methods handle the button swaps
        //they change back and forth between hosting and attending
        //and change the button text accrodingly.

        hostingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view events hosting
                mGridview.setAdapter(null);
                GetEventIdsAsync hostIdsAsync = new GetEventIdsAsync(0, HostingEventsFragment.this);
                hostIdsAsync.execute("");
                attendingBtn.setBackgroundResource(android.R.drawable.btn_default);
                hostingBtn.setBackgroundColor(Objects.requireNonNull(getActivity()).getColor(R.color.colorPrimary));
            }
        });

        attendingBtn.setBackgroundColor(getActivity().getColor(R.color.colorPrimary));
        attendingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridview.setAdapter(null);
                //view events attending
                hostIdsAsync = new GetEventIdsAsync(1, HostingEventsFragment.this);
                hostIdsAsync.execute("");
                hostingBtn.setBackgroundResource(android.R.drawable.btn_default);
                attendingBtn.setBackgroundColor(Objects.requireNonNull(getActivity()).getColor(R.color.colorPrimary));

            }
        });

        // view events from profiles
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = DBUtils.getEventDataNotSeralizable(currentEvents.get(position));
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().
                        replace(R.id.home_fragment_container, ViewEvent.newInstance(b)).
                        addToBackStack(null).commit();
            }
        });
    }
    

    @Override
    public void dbHostingEventStrings(ArrayList<String> dbEvents) {
        nextTask = new GetDefinedEventsAsync(this, dbEvents);
        nextTask.execute("");
    }

    @Override
    public void dbHostingEvents(ArrayList<Event> dbEvents) {
        //set adapter
        currentEvents = dbEvents;
        EventAdapter hsa = new EventAdapter(getContext(), dbEvents);
        mGridview.setAdapter(hsa);
    }
}
