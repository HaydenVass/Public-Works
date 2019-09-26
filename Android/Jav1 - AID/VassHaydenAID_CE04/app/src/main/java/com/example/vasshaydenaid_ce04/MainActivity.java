package com.example.vasshaydenaid_ce04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mListview;
    ArrayList postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListview = findViewById(R.id.cell);
        postList = new ArrayList();
        createPost();
        findListView();
        setUpBaseAdapter();

    }
    private void findListView() {
        try {
            mListview = findViewById(R.id.cell);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setUpBaseAdapter(){
        if(mListview != null){
            PostAdapter pa = new PostAdapter(this, postList);
            mListview.setAdapter(pa);
        }
    }
    //todo: create post objects.
    private void createPost(){
        String [] userArray = {"Mary Smith", "Bill Bradfield" , "Bob B. Buster", "Jacob Chase", "Steve Merbler"};
        String[] dateArray = {"2/2/2019", "4/5/2018", "3/12/2018", "4/5/2018", "3/12/2018"};
        int[] postTitle = {R.string.awesome, R.string.could_be_better, R.string.bad, R.string.love, R.string.horrible};
        String[] content = {"Great app, use it everyday. Always has a ton of options.", "Not bad, could be better though.",
                "Lags all the time. Some serious buffering issues.", "Ton of options. I always find what I'm looking for.",
                "So slow. I cant even use it on my phone."};
        int[] userImages = {R.drawable.bo1, R.drawable.boy3, R.drawable.girl4, R.drawable.man3, R.drawable.man2};
        int[] ratings = {R.drawable.fivestars, R.drawable.fourstars, R.drawable.twostars, R.drawable.fourstars,
        R.drawable.onestar};

        for (int i = 0; i < userArray.length; i++) {
            Post postToAdd = new Post(userArray[i], dateArray[i], postTitle[i], content[i], userImages[i],
                    R.drawable.showmore, ratings[i], R.drawable.like);
            postList.add(postToAdd);
        }
    }
}
