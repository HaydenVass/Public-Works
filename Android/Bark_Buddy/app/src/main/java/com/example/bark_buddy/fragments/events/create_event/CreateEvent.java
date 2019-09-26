package com.example.bark_buddy.fragments.events.create_event;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;
import com.example.bark_buddy.fragments.events.host_events.HostingEventsFragment;
import com.example.bark_buddy.objects.Event;
import com.example.bark_buddy.utils.DBUtils;
import com.example.bark_buddy.utils.DateTimeUtil;
import com.google.firebase.firestore.GeoPoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class CreateEvent extends Fragment {

    private static final String TAG = "today";
    private byte[] eventImgbytes;
    private boolean imageUploaded = false;
    private CreateNewEvent listener;

    public static CreateEvent newInstance() {
        Bundle args = new Bundle();
        CreateEvent fragment = new CreateEvent();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof CreateNewEvent){
            listener = (CreateNewEvent) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_event_fragment1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //set host name
        final TextView host = Objects.requireNonNull(getActivity()).findViewById(R.id.textView_create_event_host);
        DBUtils.setTextViewWithUserData(host, getResources().getString(R.string.ops));
        //set host img
        ImageView hostImg = getActivity().findViewById(R.id.imageView_view_profileImg_host);
        Bitmap errorIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error_outline_black_24dp);
        DBUtils.setImageViewFromDB(hostImg, errorIcon);

        //image view - add image
        ImageView addEventImg = getActivity().findViewById(R.id.imageView_view_event);
        addEventImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent getImageIntent = new Intent(Intent.ACTION_PICK);
                    getImageIntent .setType("image/*");
                    startActivityForResult(getImageIntent, 3);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //spinner details
        final Spinner energySpinner = getActivity().findViewById(R.id.TextView_view_event_energy);
        final Spinner weightSpinner = getActivity().findViewById(R.id.Spinner_view_event_weight);

        //date
        TextView setDate = getActivity().findViewById(R.id.textView_c_date);
        final TextView pickedDate = getActivity().findViewById(R.id.textView_view_event_date);
        DateTimeUtil.setDatePickerOnViewClick(getContext(), setDate, pickedDate);

        //Start time
        final TextView pickedTime = getActivity().findViewById(R.id.textView_view_event_starttime);
        final TextView startTime = getActivity().findViewById(R.id.textView_c_starttime);
        DateTimeUtil.setTimePickerOnViewClick(getContext(), startTime, pickedTime);
        DateTimeUtil.setTimePickerOnViewClick(getContext(), pickedTime, pickedTime);

        //end time
        final TextView pickedEndTime = getActivity().findViewById(R.id.textView_view_event_endtime);
        final TextView endTime = getActivity().findViewById(R.id.textView_c_endtime);
        DateTimeUtil.setTimePickerOnViewClick(getContext(), endTime, pickedEndTime);
        DateTimeUtil.setTimePickerOnViewClick(getContext(), pickedEndTime, pickedEndTime);

        //location
        final EditText locationText = getActivity().findViewById(R.id.Textview_view_form_location_address);

        //cancel button
        Button cancelButton = getActivity().findViewById(R.id.button_c_event_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        HostingEventsFragment.newInstance()).commit();
            }
        });

        //next button
        Button nextButton = getActivity().findViewById(R.id.button_c_event_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //todo - validation
                if(!pickedDate.getText().toString().isEmpty() && !startTime.getText().toString().isEmpty()
                && !endTime.getText().toString().isEmpty() && !locationText.getText().toString().isEmpty()
                && imageUploaded){

                    //check to see if location is valid
                    GeoPoint locationPoint = getGeoPointFromAddress(locationText.getText().toString());
                    Log.i(TAG, "onClick: " + locationPoint);
                    if(locationPoint == null){

                        //validation for if the address was not a valid address
                        Toast.makeText(getContext(),getResources()
                                .getString(R.string.nonValidLocal),Toast.LENGTH_SHORT).show();

                    }else{
                        Event newEvent = new Event(eventImgbytes, energySpinner.getSelectedItem().toString(),
                                weightSpinner.getSelectedItem().toString(), DBUtils.getCurrentUser().getUid(),
                                pickedDate.getText().toString(), pickedTime.getText().toString(),
                                pickedEndTime.getText().toString(), locationText.getText().toString());
                        newEvent.setGeoPointLocation(locationPoint);
                        newEvent.setHost(host.getText().toString());

                        listener.newEvent(newEvent);
                        //transition to finish form
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().
                                beginTransaction().replace(R.id.home_fragment_container,
                                CreateEventP2.newInstance()).addToBackStack(null).commit();
                    }

                }else{
                    //form validation - all fields have to be filled out
                    Toast.makeText(getContext(),getResources().getString(R.string.fillAllForms),
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK && requestCode== 3) {
            try{
                // call back from the IMG PICK intent
                // takes image -- places it into image view
                Uri fullPhotoUri = data.getData();
                ImageView iv = Objects.requireNonNull(getActivity()).findViewById(R.id.imageView_view_event);
                if(iv != null && fullPhotoUri != null){
                    //covnerts to byte array to be uploaded to the DB
                    iv.setImageURI(fullPhotoUri);
                    Bitmap bitmap = ((BitmapDrawable)iv.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                    eventImgbytes = stream.toByteArray();
                    //switch to make sure an image was uploaded to the image view.
                    imageUploaded = true;
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getContext(), getResources().getString(R.string.wentWrong), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), getResources().getString(R.string.wentWrong), Toast.LENGTH_SHORT).show();
        }
    }

    private GeoPoint getGeoPointFromAddress(String strAddress){
        Geocoder coder = new Geocoder(getContext());
        List<Address> address;
        GeoPoint geo;
        try {
            //takes String and checks to see if its a valid address.
            address = coder.getFromLocationName(strAddress,5);
            if (address == null || address.isEmpty()) {
                return null;
            }
            //if its a valid address, a new location object is made
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            //once the location object is made, a new geo point object is made
            geo = new GeoPoint( (location.getLatitude()), (location.getLongitude()));

            return geo;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

    //passes event obj to the HomeActivity
    public interface CreateNewEvent{
        void newEvent(Event newEvent);
    }
}
