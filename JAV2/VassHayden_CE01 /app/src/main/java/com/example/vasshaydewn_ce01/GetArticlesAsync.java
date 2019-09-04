//Hayden Vass
//JAV2 - 1905
//GetArticlesAsync

package com.example.vasshaydewn_ce01;

import android.os.AsyncTask;
import com.example.vasshaydewn_ce01.Objects.Article;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

class GetArticlesAsync extends AsyncTask<Void, Void, ArrayList<Article>> {

    private final String selectedAPI;
    private final OnFinished listener;
    private final int sender;

    GetArticlesAsync(String _selectedAPI, OnFinished _finished, int _sender) {
        selectedAPI = _selectedAPI;
        listener = _finished;
        sender = _sender;
    }

    @Override
    protected ArrayList<Article> doInBackground(Void... voids) {
        String data = null;
        ArrayList<Article> articles = new ArrayList<>();
        try{
            // pulls inital data from API
            data = NetworkUtils.getNetworkData(selectedAPI);
        }catch(IOException e){
            e.printStackTrace();
        }

        try{
            // parses through each level of JSON
            JSONObject response = new JSONObject(data);
            JSONObject test = response.getJSONObject("data");
            JSONArray innerArray = test.getJSONArray("children");
            for (int i = 0; i < innerArray.length(); i++) {
               JSONObject innerObj = innerArray.getJSONObject(i);
               JSONObject redditData = innerObj.getJSONObject("data");

               // get info from each post
               String title = redditData.getString("title");
               String author = redditData.getString("author_fullname");
               String ups = String.valueOf(redditData.getInt("ups"));

               // add to array
               articles.add(new Article(title, author, ups));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articleArrayList) {
        //sends data back to main activity to populate appropriate list
        listener.SendArticlesBack(articleArrayList, sender);
    }
    //interface to send data back to activity
    public interface OnFinished {
        void SendArticlesBack(ArrayList<Article> pulledArticles, int sender);
    }
}
