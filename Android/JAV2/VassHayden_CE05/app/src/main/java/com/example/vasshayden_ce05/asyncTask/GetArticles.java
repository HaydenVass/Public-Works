//Hayden Vass
// Jav2 1905
//GetArticles
package com.example.vasshayden_ce05.asyncTask;
import android.os.AsyncTask;
import com.example.vasshayden_ce05.utils.NetworkUtils;
import com.example.vasshayden_ce05.objects.Article;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

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
            // parses through each level of JSON
            JSONObject response = new JSONObject(data);
            JSONObject test = response.getJSONObject("data");
            JSONArray innerArray = test.getJSONArray("children");
            for (int i = 0; i < innerArray.length(); i++) {
                JSONObject innerObj = innerArray.getJSONObject(i);
                JSONObject redditData = innerObj.getJSONObject("data");
                // get info from each post
                String title = redditData.getString("title");
                String body = redditData.getString("author_fullname");
                String imgURL = redditData.getString("thumbnail");
                // add to array
                articles.add(new Article(title, body, imgURL));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articleArrayList) {
        //sends data back to main activity to populate appropriate list
        listener.SendArticlesBack(articleArrayList);
    }
    //interface to send data back to activity
    public interface OnFinished {
        void SendArticlesBack(ArrayList<Article> pulledArticles);
    }

}
