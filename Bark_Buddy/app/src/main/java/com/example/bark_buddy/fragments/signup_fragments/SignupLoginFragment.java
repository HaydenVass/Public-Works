package com.example.bark_buddy.fragments.signup_fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bark_buddy.R;
import com.example.bark_buddy.activities.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class SignupLoginFragment extends Fragment {
    private FirebaseAuth mAuth;

    public SignupLoginFragment() {
    }

    public static SignupLoginFragment newInstance() {
        Bundle args = new Bundle();
        SignupLoginFragment fragment = new SignupLoginFragment();
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
        return inflater.inflate(R.layout.login_signup_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        Button login = Objects.requireNonNull(getActivity()).findViewById(R.id.button_login);
        Button signup = getActivity().findViewById(R.id.button_signup);

        final EditText email = getActivity().findViewById(R.id.editText_e_mail);
        final EditText pword = getActivity().findViewById(R.id.editText_pword);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takes user to sign up process
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        SignUpFragment.newInstance()).addToBackStack(null).commit();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String password = pword.getText().toString();

                mAuth.signInWithEmailAndPassword(userEmail, password).
                        addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //if password and email are correct the user is logged into the application
                                    Intent i= new Intent (getActivity(), HomeActivity.class);
                                    startActivity(i);
                                }else{
                                    Toast.makeText(getContext(), getResources().getString(R.string.logInInfoNoMatch),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

}
