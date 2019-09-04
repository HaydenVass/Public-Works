package com.example.bark_buddy.fragments.signup_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;
import com.example.bark_buddy.utils.GalleryUtil;


import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class SignUpFragmentP2 extends Fragment {
    private AddMoreUserData listener;
    private boolean vacWasUploaded = false;
    private byte[] vacBytes;

    public SignUpFragmentP2() {
    }

    public static SignUpFragmentP2 newInstance() {
        Bundle args = new Bundle();
        SignUpFragmentP2 fragment = new SignUpFragmentP2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof AddMoreUserData){
            listener = (AddMoreUserData) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up_fragment_p2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity a = getActivity();
        vacBytes = new byte[]{};
        //Text fields
        final EditText doggoName = Objects.requireNonNull(a).findViewById(R.id.editText_dog_name);
        final EditText doggoBreed = a.findViewById(R.id.editText_dog_breed);

        //spinners
        final Spinner energyLevelSpinner = a.findViewById(R.id.spinner_energy_level);
        final Spinner weightSpinner = a.findViewById(R.id.spinner_weighjt);

        //image for vaccines
        final ImageView vacImg = a.findViewById(R.id.imageView_vacs);
        vacImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(GalleryUtil.openGallery(), 1);
            }
        });

        //validate
        Button nextButton = a.findViewById(R.id.button_next2);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dogName = doggoName.getText().toString();
                String dogBreed = doggoBreed.getText().toString();
                String energy = energyLevelSpinner.getSelectedItem().toString();
                String weight = weightSpinner.getSelectedItem().toString();

                if(!dogName.isEmpty() && !dogBreed.isEmpty() && vacWasUploaded){
                    listener.BreedNameWeightEnergy(dogName, dogBreed, energy, weight, vacBytes);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            SignUpFragmentP3.newInstance()).addToBackStack(null).commit();
                }else{
                    //toast
                    Toast.makeText(getContext(),getResources().
                            getString(R.string.fillAllForms),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //sends this bit of data back to MainActivity
    public interface AddMoreUserData{
        void BreedNameWeightEnergy(String name, String breed, String energyLevel, String weight, byte[] vacImg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //on activity result for uploading the image of the vac report
        //converts to bitmap for upload to the DB and triggers a flag for validation.
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== 1  && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();

            ImageView iv = Objects.requireNonNull(getActivity()).findViewById(R.id.imageView_vacs);
            iv.setImageURI(fullPhotoUri);

            Bitmap bitmap = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            vacBytes = stream.toByteArray();

            vacWasUploaded = true;
        }
    }
}
