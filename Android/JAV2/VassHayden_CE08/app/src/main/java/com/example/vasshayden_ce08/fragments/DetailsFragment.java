//Hayden Vass
//jav2-1905
//details fragment
package com.example.vasshayden_ce08.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.vasshayden_ce08.MainActivity;
import com.example.vasshayden_ce08.R;
import com.example.vasshayden_ce08.objects.Student;
import com.example.vasshayden_ce08.utils.WebUtils;

import java.util.Objects;

public class DetailsFragment extends Fragment {

    private static final String BUNDLE_KEY = "passedStudent";
    public DetailsFragment() {
    }

    //takes in selected student to get details
    public static DetailsFragment newInstance(Student student) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_KEY, student);
        DetailsFragment fragment = new DetailsFragment();
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
        return inflater.inflate(R.layout.details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert getArguments() != null;
        //sets up web view
        Student s = (Student) getArguments().getSerializable(BUNDLE_KEY);
        WebView webView = Objects.requireNonNull(getView()).findViewById(R.id.details_webview);
        WebUtils.setupWebView(webView, getContext(), s);
        WebUtils.webViewLoad(webView, MainActivity.URI_BEGINNING + MainActivity.LINK_PATH_DETAILS);
    }
}
