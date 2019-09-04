package com.example.bark_buddy.fragments.events.view_events;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bark_buddy.R;
import com.example.bark_buddy.fragments.profile.ProfileFragment;
import com.example.bark_buddy.utils.DBUtils;

import java.util.Objects;

public class ViewEvent extends Fragment {

    private static final String BUNDLE_KEY = "bundle key";
    private ViewEventButtonAsync viewEventButtonAsync;
    public static ViewEvent newInstance(Bundle selectedEvent) {
        Bundle args = new Bundle();
        args.putBundle(BUNDLE_KEY, selectedEvent);
        ViewEvent fragment = new ViewEvent();
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
        return inflater.inflate(R.layout.event_view_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert getArguments() != null;
        //get event from bundle
        final Bundle chosenEvent = getArguments().getBundle(BUNDLE_KEY);
        //set img view
        ImageView iv = Objects.requireNonNull(getActivity()).findViewById(R.id.imageView_view_event);
        Bitmap bmp = BitmapFactory.decodeByteArray(Objects.requireNonNull(chosenEvent).getByteArray("imgBytes"), 0
        , Objects.requireNonNull(chosenEvent.getByteArray("imgBytes")).length);
        iv.setImageBitmap(bmp);
        //set energy level
        TextView energyLevel = getActivity().findViewById(R.id.TextView_view_event_energy);
        energyLevel.setText(chosenEvent.getString("energyLevel"));
        //set weight range
        TextView weightRange = getActivity().findViewById(R.id.Spinner_view_event_weight);
        weightRange.setText(chosenEvent.getString("weightRange"));
        //set date
        TextView date = getActivity().findViewById(R.id.textView_view_event_date);
        date.setText(chosenEvent.getString("date"));
        //set time
        TextView startTime = getActivity().findViewById(R.id.textView_view_event_starttime);
        TextView endTime = getActivity().findViewById(R.id.textView_view_event_endtime);
        startTime.setText(chosenEvent.getString("startTime"));
        endTime.setText(chosenEvent.getString("endtime"));
        //set location
        TextView location = getActivity().findViewById(R.id.Textview_view_form_location_address);
        location.setText(chosenEvent.getString("location"));
        //set host
        TextView host = getActivity().findViewById(R.id.textView_create_event_host);
        host.setText(chosenEvent.getString("host"));
        //host picture
        ImageView hostIV = getActivity().findViewById(R.id.imageView_view_profileImg_host);
        Bitmap errorIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error_outline_black_24dp);
        DBUtils.setTargetUserImg(chosenEvent.getString("hostId"), hostIV, errorIcon);
        //description
        TextView description = getActivity().findViewById(R.id.textView_eventview_description);
        description.setText(chosenEvent.getString("description"));
        //triggers
        TextView triggers = getActivity().findViewById(R.id.textView_eventview_posTrigs);
        triggers.setText(chosenEvent.getString("Triggers"));
        //onclick listner to take people to other user provies
        hostIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        ProfileFragment.newInstance(chosenEvent.getString("hostId"), false))
                        .addToBackStack(null).commit();
            }
        });

        final Button attendBtn = getActivity().findViewById(R.id.button_event_view_attend);
        final FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        //set button async so it can be canceld and ui elements wont be set when they are off screen.
        viewEventButtonAsync = new ViewEventButtonAsync(chosenEvent.getString("eventId"), attendBtn,
                getContext(), ft);
        viewEventButtonAsync.execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(viewEventButtonAsync!=null){
            viewEventButtonAsync.cancel(true);
        }
    }
}
