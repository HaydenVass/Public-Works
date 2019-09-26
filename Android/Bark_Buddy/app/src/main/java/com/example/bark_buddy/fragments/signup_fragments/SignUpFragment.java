package com.example.bark_buddy.fragments.signup_fragments;

import android.app.Activity;
import android.content.Context;
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
import com.example.bark_buddy.objects.User;

import java.util.Objects;

public class SignUpFragment extends Fragment {
    private MakeNewUser listener;

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MakeNewUser){
            listener = (MakeNewUser) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity a = getActivity();
        //edit text
        final EditText emailET = Objects.requireNonNull(a).findViewById(R.id.editText_e_mail);
        final EditText passwordET = a.findViewById(R.id.editText_pword);
        final EditText passwordConfirmEt = a.findViewById(R.id.editText_password_confirm);

        Button nextButton = a.findViewById(R.id.button_next1);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String pw = passwordET.getText().toString();
                String pwConfirm = passwordConfirmEt.getText().toString();
                //validate to make sure all the information is filled and correct
                if(pw.equals(pwConfirm) && !pw.isEmpty() && !email.isEmpty()){
                    listener.NewUserData(new User(email, pw));

                }else{
                    if(!pw.equals(pwConfirm)){
                        //toast
                        Toast.makeText(getContext(), getResources().getString(R.string.pwNoMatch),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    //sends data back to MainActivity
    public interface MakeNewUser{
        void NewUserData(User newUser);
    }
}
