package com.example.bark_buddy.fragments.signup_fragments;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;
import com.example.bark_buddy.utils.GalleryUtil;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class SignUpFragmentP3 extends Fragment {

    private FinishNewUserData listener;
    private byte[] profileImgBytes;
    private boolean hasImg = false;
    public SignUpFragmentP3() {
    }

    public static SignUpFragmentP3 newInstance() {

        Bundle args = new Bundle();

        SignUpFragmentP3 fragment = new SignUpFragmentP3();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FinishNewUserData){
            listener = (FinishNewUserData)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up_fragment_p3, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button finishButton = Objects.requireNonNull(getActivity()).findViewById(R.id.button_finish);

        ImageView dogImage = getActivity().findViewById(R.id.imageView_dogIV);
        dogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(GalleryUtil.openGallery(), 2);
            }
        });

        //descritption
        final EditText descriptionET = getActivity().findViewById(R.id.editText_description);
        final EditText triggersET = getActivity().findViewById(R.id.editText_triggers);

        //intent for home page
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!descriptionET.getText().toString().equals("") && !triggersET.getText().toString().equals("") &&
                hasImg){
                    //sends data back to activity to finish log in processs
                    String description = descriptionET.getText().toString();
                    String triggers = triggersET.getText().toString();
                    listener.FinalDescription(description, triggers, profileImgBytes);
                }else{
                    //validates form is completely filled
                    Toast.makeText(getContext(), getResources().getString(R.string.fillAllForms),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //once everything is finished, complete account - save in firebase

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== 2  && resultCode == RESULT_OK) {

            Uri fullPhotoUri = data.getData();
            ImageView iv = Objects.requireNonNull(getActivity()).findViewById(R.id.imageView_dogIV);
            iv.setImageURI(fullPhotoUri);
            hasImg = true;

            Bitmap bitmap = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            profileImgBytes = stream.toByteArray();
        }
    }

    public interface FinishNewUserData{
        void FinalDescription(String description, String triggers, byte[] profImgBytes);
    }
}
