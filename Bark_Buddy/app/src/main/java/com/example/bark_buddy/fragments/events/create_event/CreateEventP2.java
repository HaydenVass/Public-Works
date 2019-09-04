package com.example.bark_buddy.fragments.events.create_event;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;
import com.example.bark_buddy.fragments.events.host_events.HostingEventsFragment;

import java.util.Objects;


public class CreateEventP2 extends Fragment {

    private CreateNewEventP2 listener;

    public static CreateEventP2 newInstance() {
        Bundle args = new Bundle();
        CreateEventP2 fragment = new CreateEventP2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof CreateNewEventP2){
            listener = (CreateNewEventP2) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_event_fragment2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final EditText description = Objects.requireNonNull(getActivity()).findViewById(R.id.editText_description);
        final EditText trigs = getActivity().findViewById(R.id.edittext_triggers);
        Button postButton = getActivity().findViewById(R.id.button_post);
        Button cancelButton = getActivity().findViewById(R.id.button_cancelBtn_2);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        HostingEventsFragment.newInstance()).commit();
            }
        });


        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!description.getText().toString().isEmpty() && !trigs.getText().toString().isEmpty()){
                    //protocol to activity
                    listener.newEventP2(description.getText().toString(), trigs.getText().toString());
                    //switches back to home
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                            HostingEventsFragment.newInstance()).commit();
                }else{
                    //tells user to fill out form completely
                    Toast.makeText(getContext(), getResources().
                            getString(R.string.fillAllForms), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //passes the rest of the text to the HomeActivity
    public interface CreateNewEventP2{
        void newEventP2(String description, String triggers);
    }
}
