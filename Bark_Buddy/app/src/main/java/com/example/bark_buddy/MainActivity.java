package com.example.bark_buddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bark_buddy.activities.HomeActivity;
import com.example.bark_buddy.fragments.signup_fragments.SignUpFragment;
import com.example.bark_buddy.fragments.signup_fragments.SignUpFragmentP2;
import com.example.bark_buddy.fragments.signup_fragments.SignUpFragmentP3;
import com.example.bark_buddy.fragments.signup_fragments.SignupLoginFragment;
import com.example.bark_buddy.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SignUpFragment.MakeNewUser,
        SignUpFragmentP2.AddMoreUserData, SignUpFragmentP3.FinishNewUserData {


    //todo - log user in after inital sign in;

    private static final String TAG = "today";
    private FirebaseUser currentUser;
    //private DatabaseReference mDatabase;
    private User nU = null;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private static final int REQUEST_LOCATION_PERMISSION = 0x01002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    SignupLoginFragment.newInstance()).commit();

        } else {
            //keeps requesting untill permission is granted
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MainActivity.REQUEST_LOCATION_PERMISSION);
        }

    }


    //request location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MainActivity.REQUEST_LOCATION_PERMISSION) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                        SignupLoginFragment.newInstance()).commit();
            }else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MainActivity.REQUEST_LOCATION_PERMISSION);
            }
        }
    }

    //part 1 call back - new user process
    @Override
    public void NewUserData(User newUser) {
        nU = newUser;
        //puts user log in info into database, if its a success - the user gets pushed to the next stage of
        //account creation
        mAuth.createUserWithEmailAndPassword(nU.getEmail(), nU.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            currentUser = mAuth.getCurrentUser();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    SignUpFragmentP2.newInstance()).addToBackStack(null).commit();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).toString(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    //part 2 call back
    @Override
    public void BreedNameWeightEnergy(String name, String breed, String energyLevel, String weight, byte[] vacImg) {
        if(nU != null){
            nU.setName(name);
            nU.setDogBreed(breed);
            nU.setEnergyLevel(energyLevel);
            nU.setWeightRange(weight);
            nU.setVacReport(vacImg);
        }
    }

    @Override
    public void FinalDescription(String description, String triggers, byte[] profImgBytes) {
        Log.i(TAG, "FinalDescription: " + currentUser.getUid());

        //call back from final form in the log in process
        //takes new user object and pushes it to the DB
        nU.setDescription(description);
        nU.setTriggers(triggers);
        nU.setFriends(new ArrayList<String>());
        nU.setUserID(currentUser.getUid());
        nU.setProfilePicture(profImgBytes);

        Map<String, Object> user = new HashMap<>();
        user.put("email", nU.getEmail());
        user.put("password", nU.getPassword());
        user.put("dog name", nU.getName());
        user.put("description", nU.getDescription());
        user.put("weight range", nU.getWeightRange());
        user.put("energy level", nU.getEnergyLevel());
        user.put("breed", nU.getDogBreed());
        user.put("triggers", nU.getTriggers());
        user.put("friends", nU.getFriends());
        user.put("uid", nU.getUserID());
        user.put("vacByteImg", Blob.fromBytes(nU.getVacReport()));
        user.put("profImgBytes", Blob.fromBytes(nU.getProfilePicture()));
        user.put("activeMessages", nU.getActiveMessges());

        //adds user details to the database - if it was successful then the user is moved to the actual application.
        db.collection("users").document(nU.getUserID()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


                mAuth.signInWithEmailAndPassword(nU.getEmail(), nU.getPassword());
                Intent i= new Intent (MainActivity.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "onFailure: failure some where");
            }
        });
    }

}
