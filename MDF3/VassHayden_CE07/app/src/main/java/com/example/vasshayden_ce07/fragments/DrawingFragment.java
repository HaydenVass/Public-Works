package com.example.vasshayden_ce07.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vasshayden_ce07.R;

public class DrawingFragment extends Fragment {
    public DrawingFragment() {
    }

    public static DrawingFragment newInstance() {
        Bundle args = new Bundle();
        DrawingFragment fragment = new DrawingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.draw_fragment, container, false);
    }
}
