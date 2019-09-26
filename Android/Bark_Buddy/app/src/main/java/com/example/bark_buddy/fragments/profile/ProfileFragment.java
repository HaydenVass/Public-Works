package com.example.bark_buddy.fragments.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;
import com.example.bark_buddy.activities.ChatActifity;
import com.example.bark_buddy.utils.DBUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class ProfileFragment extends Fragment {
    private boolean isOwnProfile;
    private SetProfButtonsAsync setButtons;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String UUID, boolean isOwnProfile) {
        Bundle args = new Bundle();
        args.putString("UUID", UUID);
        args.putBoolean("isOwnProfile", isOwnProfile);
        ProfileFragment fragment = new ProfileFragment();
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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final String targetUUID = Objects.requireNonNull(getArguments()).getString("UUID");
        isOwnProfile = getArguments().getBoolean("isOwnProfile");


        //hide buttons - show add friends and msg buttons if its not the current users profile
        final Button messageButton = Objects.requireNonNull(getActivity()).findViewById(R.id.button_message_button);
        final Button addFriendButton = getActivity().findViewById(R.id.button_add_friend);
        messageButton.setVisibility(View.GONE);
        addFriendButton.setVisibility(View.GONE);

        DocumentReference docRef = db.collection("users").document(Objects.requireNonNull(targetUUID));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    final DocumentSnapshot document = task.getResult();
                    Blob profileImgBlob = (Blob) Objects.requireNonNull(document).get("profImgBytes");
                    Bitmap bmp = BitmapFactory.decodeByteArray(Objects.requireNonNull(profileImgBlob).toBytes(), 0, profileImgBlob.toBytes().length);
                    //image
                    ImageView profileImg = Objects.requireNonNull(getActivity()).findViewById(R.id.imageView_profile_dogimg);
                    if(profileImg != null){
                        profileImg.setImageBitmap(bmp);
                    }
                    //dog name
                    TextView dogName = getActivity().findViewById(R.id.textView_profile_dogname);
                    if(dogName != null){
                        dogName.setText(Objects.requireNonNull(document.get("dog name")).toString());
                    }
                    //dog breed
                    TextView dogBreed = getActivity().findViewById(R.id.textView_profile_dogbreed);
                    if(dogBreed!=null){
                        dogBreed.setText(Objects.requireNonNull(document.get("breed")).toString());

                    }
                    //dog description
                    TextView dogDescritpion = getActivity().findViewById(R.id.textView_profile_dogDescription);
                    if(dogDescritpion!=null){
                        dogDescritpion.setText(Objects.requireNonNull(document.get("description")).toString());
                    }
                    //triggeres
                    TextView dogTriggeres = getActivity().findViewById(R.id.textView_profile_dogTriggers);
                    if(dogTriggeres!=null){
                        dogTriggeres.setText(Objects.requireNonNull(document.get("triggers")).toString());
                    }
                    //energy
                    TextView dogEnergy = getActivity().findViewById(R.id.textView_profile_energylevel);
                    if(dogEnergy!=null){
                        dogEnergy.setText(Objects.requireNonNull(document.get("energy level")).toString());
                    }
                    //weight
                    TextView dogWeight = getActivity().findViewById(R.id.textView_profile_weight_range);
                    if(dogWeight!=null){
                        dogWeight.setText(Objects.requireNonNull(document.get("weight range")).toString());
                    }

                    if(!isOwnProfile){
                        //show buttons if profile isnt the users
                        messageButton.setVisibility(View.VISIBLE);
                        addFriendButton.setVisibility(View.VISIBLE);
                    }else{
                        messageButton.setVisibility(View.GONE);
                        addFriendButton.setVisibility(View.GONE);
                    }

                    //add to friends
                    if (addFriendButton.getVisibility() == View.VISIBLE){
                        setButtons = new SetProfButtonsAsync(addFriendButton, Objects.
                                requireNonNull(document.get("uid")).toString(), getContext());
                        setButtons.execute();
                    }


                    Button messageButton = getActivity().findViewById(R.id.button_message_button);
                    if(messageButton!=null){
                        messageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DBUtils.addUserToActiveMessages(Objects.requireNonNull(document.get("uid")).toString());

                                Intent i= new Intent (getActivity(), ChatActifity.class);
                                i.putExtra("targetUUID", Objects.requireNonNull(document.get("uid")).toString());
                                startActivity(i);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(isOwnProfile){
            menu.findItem(R.id.action_logout).setVisible(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPause() {
        if(setButtons != null){
            setButtons.cancel(true);
        }
        super.onPause();
    }
}
