package com.example.vasshayden_ce01.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vasshayden_ce01.MainActivity;
import com.example.vasshayden_ce01.R;
import com.example.vasshayden_ce01.objects.SavedLocation;
import com.example.vasshayden_ce01.utils.FileUtils;

import java.util.ArrayList;
import java.util.Objects;

public class DetailsFragment extends Fragment {
    public static final String TAG = "today";
    private ArrayList<SavedLocation> allLocations;
    private SavedLocation selectedLocation;
    private DetailsCallback detailsCallback;


    public DetailsFragment() {
    }

    public static DetailsFragment newInstance(String tag) {
        Bundle args = new Bundle();
        args.putString("tag", tag);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof DetailsCallback){
            detailsCallback = (DetailsCallback)context;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.menu_save).setVisible(false);
        menu.findItem(R.id.action_take_picture).setVisible(false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
        TextView title = Objects.requireNonNull(getActivity()).findViewById
                (R.id.details_textView_title);
        TextView description = getActivity().findViewById
                (R.id.details_textView_description);
        ImageView iv = getActivity().findViewById
                (R.id.details_imageView);

        // get selected tag -- load in all current favorited
        // locations
        String tag = Objects.requireNonNull(getArguments()).getString("tag");
        allLocations = FileUtils.LoadSerializable
                (MainActivity.ALL_LOCATIONS_FILE_NAME, Objects.requireNonNull(getContext()));

        //check by uri -- which are unique
        selectedLocation = null;
        for (SavedLocation s : allLocations) {
            if(s.getUri().equals(tag)){
                selectedLocation = s;
                break;
            }
        }

        if(selectedLocation != null) {
            title.setText(selectedLocation.getName());
            description.setText(selectedLocation.getDescription());
            iv.setImageURI(Uri.parse(selectedLocation.getUri()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //snack bar for confirming deletion of saved point
        if(item.getItemId() == R.id.menu_delete){
            Snackbar snackbar = Snackbar
                    .make(Objects.requireNonNull(getActivity()).findViewById(R.id.myCoordinatorLayout),
                            getActivity().getResources().getString(R.string.confirm_delete),
                            Snackbar.LENGTH_LONG)
                    .setAction(getActivity().getResources().getString(R.string.detele), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            allLocations.remove(selectedLocation);
                            FileUtils.SaveSerializableList(MainActivity.ALL_LOCATIONS_FILE_NAME,
                                    Objects.requireNonNull(getContext()), allLocations);
                            detailsCallback.removedLocation();
                        }
                    });

            snackbar.show();
        }
        return true;
    }
    public interface DetailsCallback {
        void removedLocation();
    }
}
