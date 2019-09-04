//Hayden Vass
//Jav21905
//get articles
package com.example.vasshayden_ce07.async_task;

import android.os.AsyncTask;

import com.example.vasshayden_ce07.Utils.NetworkUtils;
import com.example.vasshayden_ce07.objects.Article;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GetArticles extends AsyncTask<Void, Void, ArrayList<Article>> {

    private final String selectedAPI;
    private final OnFinished listener;

    public GetArticles(String selectedAPI, OnFinished listener) {
        this.selectedAPI = selectedAPI;
        this.listener = listener;
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
            JSONObject response = new JSONObject(data);
            JSONObject test = response.getJSONObject("data");
            JSONArray innerArray = test.getJSONArray("children");
            for (int i = 0; i < innerArray.length(); i++) {
                JSONObject innerObj = innerArray.getJSONObject(i);
                JSONObject redditData = innerObj.getJSONObject("data");
                // get info from each post
                String title = redditData.getString("title");
                String author = redditData.getString("author_fullname");
                String imgURL = redditData.getString("thumbnail");
                int upVotes = redditData.getInt("ups");
                int score = redditData.getInt("score");
                String urlEnd = redditData.getString("permalink");
                String urlFull = "https://reddit.com" + urlEnd;
                // add to array
                articles.add(new Article(title, author, imgURL, urlFull, upVotes, score));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articleArrayList) {
        final int rInt = new Random().nextInt(articleArrayList.size());
        listener.SendArticleBack(articleArrayList.get(rInt));
    }
    //interface to send data back to ArticleJobService
    public interface OnFinished {
        void SendArticleBack(Article pulledArticle);
    }

}