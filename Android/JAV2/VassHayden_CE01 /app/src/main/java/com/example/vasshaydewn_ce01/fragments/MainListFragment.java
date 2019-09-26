//Hayden Vass
//JAV2 - 1905
//MainListFragment

package com.example.vasshaydewn_ce01.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vasshaydewn_ce01.ArticlesAdapter;
import com.example.vasshaydewn_ce01.Objects.Article;
import com.example.vasshaydewn_ce01.R;

import java.util.ArrayList;

public class MainListFragment extends ListFragment {


    public MainListFragment() {
    }

    public static MainListFragment newInstance(ArrayList<Article> articleArrayList) {
        Bundle args = new Bundle();
        args.putSerializable("ARTICLES", articleArrayList);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //creates custom list adapter, populated with the "articles" from API
        if(getArguments() != null){
            ArrayList articles = (ArrayList<Article>)getArguments().
                    getSerializable("ARTICLES");
            if (articles != null){
                ArticlesAdapter articlesAdapter = new ArticlesAdapter(getActivity(), articles);
                setListAdapter(articlesAdapter);
            }
        }

    }
}
