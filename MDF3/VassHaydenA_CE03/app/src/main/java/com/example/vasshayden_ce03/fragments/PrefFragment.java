package com.example.vasshayden_ce03.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.vasshayden_ce03.R;

public class PrefFragment extends PreferenceFragmentCompat {
    public static final String TAG = "today";
    private static final String COLOR_DISPLAY_KEY = "list_color_preference";
    private static final String CITY_LIST_KEY = "list_preference";

    public static PrefFragment newInstance() {
        Bundle args = new Bundle();
        PrefFragment fragment = new PrefFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.pref_config, s);

    }

    //returning a string
    public static String getCityPref(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(CITY_LIST_KEY, null);
    }

    public static String getDisplayPref(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(COLOR_DISPLAY_KEY, null);
    }
}
