package com.example.vasshayden_ce08.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.vasshayden_ce08.MainActivity;
import com.example.vasshayden_ce08.R;
import com.example.vasshayden_ce08.objects.Student;
import com.example.vasshayden_ce08.utils.FileUtils;
import com.example.vasshayden_ce08.utils.WebUtils;

import java.util.ArrayList;
import java.util.Objects;

public class MainListFragment extends Fragment {

    private static final String TAG = "today";
    public static final String BROADCAST_ACTION =
            "com.example.vasshayden_ce06.Fragments.BROADCAST_ACTION";
    private final ArticleReciever reciever = new ArticleReciever();

    public MainListFragment() {
    }

    public static MainListFragment newInstance() {
        Bundle args = new Bundle();
        MainListFragment fragment = new MainListFragment();
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
        return inflater.inflate(R.layout.main_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //set up web views
        WebView webView = Objects.requireNonNull(getView()).findViewById(R.id.list_web_view);
        WebUtils.setupWebView(webView, getContext(), new Student());
        WebUtils.webViewLoad(webView, MainActivity.URI_BEGINNING +
                MainActivity.LINK_PATH_LIST);

        //floating action button for adding students
        FloatingActionButton fab = getView().findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).
                        getSupportFragmentManager().beginTransaction().
                        add(R.id.fragment_container,
                                FormFragment.newInstance()).addToBackStack(null).
                        commit();
            }
        });
    }
    //broadcast reciever
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION);
        Objects.requireNonNull(getActivity()).registerReceiver(reciever, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getActivity()).unregisterReceiver(reciever);
    }
    //nested reciever class
    class ArticleReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null){
                if (intent.hasExtra("SELECTED_ITEM")){
                    //gets file name from the broadcast. The file name is just the name+number
                    //+town in string form. Its broken out to see which student was selected
                    // that student is then passed into the details view
                    String itemName = intent.getStringExtra("SELECTED_ITEM");
                    ArrayList<Student> allStudents = FileUtils.LoadSerializable
                            (MainActivity.STUDENT_FILENAME, Objects.requireNonNull(getContext()));
                    for (Student s : allStudents) {
                        String check = s.getName()+s.getStudentNumber()+s.getHomeTown();
                        if(itemName.equals(check)){

                            Objects.requireNonNull(getActivity()).
                                    getSupportFragmentManager().beginTransaction().
                                    add(R.id.fragment_container, DetailsFragment.newInstance(s)).
                                    addToBackStack(null).
                                    commit();
                        }
                    }
                }else{
                    //adding a student from the previous screen triggers an empty broadcast
                    //to replace the listfragment with the updated list.
                    Log.i(TAG, "onReceive: ");
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                    getActivity().getSupportFragmentManager().
                            beginTransaction().replace(R.id.fragment_container,
                            MainListFragment.newInstance()).commit();
                }
            }
        }
    }
}

