package com.example.bark_buddy.fragments.home_screen;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;

import java.util.Objects;

public class FilterFragment extends Fragment {

    public static final String ENERGY_KEY = "ENERGY_KEY";
    public static final String  WEIGHT_KEY = "WEIGHT_KEY";
    public static final String DISTANCE_KEY = "DISTANCE_KEY";

    public FilterFragment() { }

    public static FilterFragment newInstance() {
        Bundle args = new Bundle();
        FilterFragment fragment = new FilterFragment();
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
        return inflater.inflate(R.layout.filter_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadPrefs();

        ImageView openFilter =  Objects.requireNonNull(getActivity()).findViewById(R.id.imageView_filterImg_open);
        openFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().
                        replace(R.id.home_fragment_container,
                        HomeGridFragment.newInstance()).commit();
            }
        });


        //seek bar
        //sets the min and max of the seek bar - the min can be changed to be 1 to act more appropriately
        //currently set to 0 for testing
        final SeekBar distanceSeekBar = getActivity().findViewById(R.id.seekBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            distanceSeekBar.setMin(0);
        }
        distanceSeekBar.setMax(100);
        //seek bar listener
        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //seek bar value - text view
                TextView seekValue = Objects.requireNonNull(getActivity()).findViewById(R.id.textView_filter_distanceBarAmount);
                String string = distanceSeekBar.getProgress() + getResources().getString(R.string.miles);
                seekValue.setText(string);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        //energy level
        final Spinner energySpinner = getActivity().findViewById(R.id.spinner_energylvl);
        //weight
        final Spinner weightSpinner = getActivity().findViewById(R.id.spinner_weight_range);
        //apply button
        Button applyBtn = getActivity().findViewById(R.id.button_apply);
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saves user prefrenxes once clicked
                PreferenceManager.getDefaultSharedPreferences(getContext()).
                edit().putString(ENERGY_KEY, energySpinner.getSelectedItem().toString()).apply();

                PreferenceManager.getDefaultSharedPreferences(getContext()).
                        edit().putString(WEIGHT_KEY, weightSpinner.getSelectedItem().toString()).apply();

                PreferenceManager.getDefaultSharedPreferences(getContext()).
                        edit().putString(DISTANCE_KEY, String.valueOf(distanceSeekBar.getProgress())).apply();

                fragmentSwitch();
            }
        });

        Button cancel = getActivity().findViewById(R.id.button_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSwitch();
            }
        });


    }

    //method to load the current user prefs
    private void loadPrefs(){
        final SeekBar distanceSeekBar = Objects.requireNonNull(getActivity()).findViewById(R.id.seekBar);
        final Spinner energySpinner = getActivity().findViewById(R.id.spinner_energylvl);
        final Spinner weightSpinner = getActivity().findViewById(R.id.spinner_weight_range);

        String eLevel = PreferenceManager.getDefaultSharedPreferences(getContext()).
                getString(FilterFragment.ENERGY_KEY, "Medium");

        String wRange = PreferenceManager.getDefaultSharedPreferences(getContext()).
                getString(FilterFragment.WEIGHT_KEY, "Medium");

        String distance = PreferenceManager.getDefaultSharedPreferences(getContext()).
                getString(FilterFragment.DISTANCE_KEY, "25");

        distanceSeekBar.setProgress(Integer.valueOf(Objects.requireNonNull(distance)));
        int ePos = 0;
        switch(Objects.requireNonNull(eLevel)){
            case "Low":
                ePos = 1;
                break;
            case "Medium":
                ePos = 2;
                break;
            case "High":
                ePos = 3;
                break;
        }
        energySpinner.setSelection(ePos);

        int wPos = 0;
        switch(Objects.requireNonNull(wRange)){
            case "Less than 7":
                wPos = 1;
                break;
            case "7 – 15":
                wPos = 2;
                break;
            case "16 – 35":
                wPos = 3;
                break;
            case "36 – 50":
                wPos = 4;
                break;
            case "50 – 75":
                wPos = 5;
                break;
            case "76 or above":
                wPos = 6;
                break;

        }
        weightSpinner.setSelection(wPos);
        TextView seekValue = getActivity().findViewById(R.id.textView_filter_distanceBarAmount);
        seekValue.setText(distanceSeekBar.getProgress() + " miles");
    }


    private void fragmentSwitch(){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().
                beginTransaction().replace(R.id.home_fragment_container,
                HomeGridFragment.newInstance()).commit();
    }
}
