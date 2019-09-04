//Hayden Vass
//Jav21905
//Main
package com.example.vasshayden_ce05;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.vasshayden_ce05.asyncTask.GetArticles;
import com.example.vasshayden_ce05.fragments.MainListFragment;
import com.example.vasshayden_ce05.objects.Article;
import com.example.vasshayden_ce05.utils.DatabaseHelper;
import com.example.vasshayden_ce05.utils.NetworkUtils;
import com.example.vasshayden_ce05.utils.NotificationsUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetArticles.OnFinished{

    private DatabaseHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbh = DatabaseHelper.getInstance(this);
        if(NetworkUtils.isConnected(this)){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    MainListFragment.newInstance()).commit();
            getAPIData("https://www.reddit.com/r/puppies/hot.json");

        }else{
            NotificationsUtils.presentToast(this, getString(R.string.no_network));
        }
    }

    //starts API task - compiler gets mad that only one API is used, but making a method less dynamic
    //doesnt seem right.
    private void getAPIData(String _selectedAPI){
        GetArticles mountainTask = new GetArticles(_selectedAPI, this);
        mountainTask.execute();
    }

    //call back from API call
    @Override
    public void SendArticlesBack(ArrayList<Article> pulledArticles) {
        Cursor c = dbh.getAllData();
        if (c.getCount() == 0){
            for (Article a : pulledArticles) {
                dbh.insertArticle(a.getArticleName(), a.getBody(), a.getImgURL());
            }
        }
    }
}
