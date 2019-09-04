package com.example.vasshayden_ce04.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.vasshayden_ce04.R;

public class ConfigFragment extends PreferenceFragmentCompat {

    public static final String TAG = "today";
    private static final String SCROLL_KEY = "checkbox_preference";


    public static ConfigFragment newInstance() {
        Bundle args = new Bundle();
        ConfigFragment fragment = new ConfigFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.config, rootKey);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static Boolean getScrollPref(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(SCROLL_KEY, true);
    }
}
