package com.example.vasshayden_ce07.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.vasshayden_ce07.R;
import com.example.vasshayden_ce07.activities.CreditsActivity;
import com.example.vasshayden_ce07.activities.DrawingActivity;

import java.util.Objects;

public class MainMenuFragment extends Fragment {

    public static final String TAG = "today";

    public MainMenuFragment() {
    }

    public static MainMenuFragment newInstance() {
        Bundle args = new Bundle();
        MainMenuFragment fragment = new MainMenuFragment();
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
        return inflater.inflate(R.layout.start_screen_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //play button
        Button playButton = Objects.requireNonNull(getActivity()).findViewById(R.id.start_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(getContext(), DrawingActivity.class);
                startActivity(playIntent);
            }
        });

        //credits
        Button creditsButton = getActivity().findViewById(R.id.credits_button);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creditsIntent = new Intent(getContext(), CreditsActivity.class);
                startActivity(creditsIntent);
            }
        });

    }
}
