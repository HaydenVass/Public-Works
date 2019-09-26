//Hayden Vass
//MDF3 - 1906
//forecast frag

package com.example.vasshayden_ce03.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vasshayden_ce03.R;
import com.example.vasshayden_ce03.adapters.ForecastAdapter;
import com.example.vasshayden_ce03.objects.WeatherReport;
import com.example.vasshayden_ce03.utils.FileUtils;

import java.util.ArrayList;
import java.util.Objects;

public class ForecastFragment extends ListFragment {

    public static final String TAG = "today";
    public ForecastFragment() {
    }

    public static ForecastFragment newInstance() {
        Bundle args = new Bundle();
        ForecastFragment fragment = new ForecastFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //text view for time stamp
        TextView tv = Objects.requireNonNull(getActivity()).findViewById(R.id.listview_timestamp);

        //gets unique list based off selected city - each city has their own saved file
        String fileName = PrefFragment.getCityPref(getContext()).substring(0,2) + "forecast";
        ArrayList<WeatherReport> reports = FileUtils.LoadWeeklyForecastSerializable(Objects.requireNonNull(getContext()),
                fileName);
//
        String timeStampe = getContext().getString(R.string.listview_timestampe)
                + " " + reports.get(0).getTime();
        tv.setText(timeStampe);
        //sets adapter for list view
        ForecastAdapter weatherAdapter = new ForecastAdapter(getActivity(), reports);
        setListAdapter(weatherAdapter);
    }
}
