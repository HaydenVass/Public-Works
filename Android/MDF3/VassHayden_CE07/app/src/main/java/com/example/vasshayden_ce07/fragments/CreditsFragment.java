package com.example.vasshayden_ce07.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vasshayden_ce07.R;

public class CreditsFragment extends Fragment {
    public CreditsFragment() {
    }

    public static CreditsFragment newInstance() {
        Bundle args = new Bundle();
        CreditsFragment fragment = new CreditsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.credits_fragment, container, false);
    }
}
