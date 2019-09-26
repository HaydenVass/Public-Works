//Hayden Vass
//jav2-1905
//form fragment
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


public class FormFragment extends Fragment {

    public FormFragment() {
    }

    public static FormFragment newInstance() {
        Bundle args = new Bundle();
        FormFragment fragment = new FormFragment();
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
        return inflater.inflate(R.layout.form_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        WebView webView = Objects.requireNonNull(getView()).findViewById(R.id.form_frag_webview);
        WebUtils.setupWebView(webView, getContext(), new Student());
        WebUtils.webViewLoad(webView,MainActivity.URI_BEGINNING + MainActivity.LINK_PATH_FORM);
    }

}
