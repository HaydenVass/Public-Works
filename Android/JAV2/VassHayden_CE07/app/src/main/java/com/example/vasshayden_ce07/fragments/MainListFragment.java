//Hayden Vass
//Jav21905
//main list fragment
package com.example.vasshayden_ce07.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.vasshayden_ce07.MainActivity;
import com.example.vasshayden_ce07.R;
import com.example.vasshayden_ce07.Utils.FileUtils;
import com.example.vasshayden_ce07.Utils.JobUtils;
import com.example.vasshayden_ce07.Utils.NotificationsUtils;
import com.example.vasshayden_ce07.adapters.ArticleAdapter;
import com.example.vasshayden_ce07.objects.Article;

import java.util.ArrayList;
import java.util.Objects;

public class MainListFragment extends ListFragment {


    private static final String LOAD_ARTICLE_LIST_KEY = "ARTICLE_ARRAY";
    public static final int JOB_ID = 0x0001;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Article> articlesToSend =
                FileUtils.LoadSerializableArray(LOAD_ARTICLE_LIST_KEY, Objects.requireNonNull(getActivity()));
        setAdapter(articlesToSend);
        getListView().setOnItemClickListener(listItemClicked);
        JobUtils.startJobService(MainListFragment.JOB_ID, true, "pup", getContext());

    }

    private final AdapterView.OnItemClickListener listItemClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ArrayList<Article> a = FileUtils.LoadSerializableArray(LOAD_ARTICLE_LIST_KEY, Objects.requireNonNull(getActivity()));
            Article sa = a.get(position);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sa.getArticleUrl()));
            startActivity(browserIntent);

        }
    };

    //reciever based methods
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
                //loads already saved articles, gets newly saved article, then appends it to the array
                //to be resaved. Im doing it this way because while researching appending objects with
                //ObjectOutputStream, it seemed very easy to end up with a corrupted file. Rather,
                //I'm just taking the previous values, appending in code then re writing over the
                //previous file.
                ArrayList<Article> allArticles =
                        FileUtils.LoadSerializableArray(LOAD_ARTICLE_LIST_KEY, Objects.requireNonNull(getActivity()));
                Article savedArticle = FileUtils.saveArticle(getActivity());
                allArticles.add(savedArticle);
                setAdapter(allArticles);
                FileUtils.SaveSerializableArray(LOAD_ARTICLE_LIST_KEY, allArticles, getActivity());
                NotificationsUtils.clearNotifications(getActivity(), MainActivity.STANDARD_NOTIFICATION);
                Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();

            }
        }
    }
    private void setAdapter(ArrayList<Article> a){
        ArticleAdapter adapter = new ArticleAdapter(getContext(), a);
        getListView().setAdapter(adapter);
    }
}
