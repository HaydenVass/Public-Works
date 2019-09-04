//Hayden Vass
// JAV1 - 1904
// GetBooksAsync

package com.example.vasshayden_ce07;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class GetBooksAsync extends AsyncTask<String, String, String> {


    private final ArrayList<Books> pulledBooks = new ArrayList<>();
    final private OnFinished mFinishedInterface;

    
    GetBooksAsync(OnFinished _finished) {
        mFinishedInterface = _finished;
    }

    @Override
    protected void onPreExecute() { }

    @Override
    protected String doInBackground(String... strings) {
        return pullBooks(strings[0]);
    }

    private String pullBooks(String webAddress){
        String result = "";
        HttpURLConnection connection = null;
        InputStream is = null;
        URL url;
        try{
            url = new URL(webAddress);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            assert connection != null;
            is = connection.getInputStream();
            result = IOUtils.toString(is, "UTF-8");
            String title;
            String imgURL;
            //initial object
            JSONObject outterObj = new JSONObject(result);
            JSONArray innerArray = outterObj.getJSONArray("items");
            // loop through array to pull title and image url
            for (int i = 0; i < innerArray.length(); i++) {
                JSONObject innerObj = innerArray.getJSONObject(i);
                JSONObject volumeInfo = innerObj.getJSONObject("volumeInfo");
                //set title
                title = volumeInfo.getString("title");
                JSONObject imgURLStr = volumeInfo.getJSONObject("imageLinks");
                //set img url
                imgURL = imgURLStr.getString("thumbnail");
                Books bookToAdd = new Books(title,imgURL);
                pulledBooks.add(bookToAdd);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(connection != null){
                try{
                    assert is != null;
                    is.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            assert connection != null;
            connection.disconnect();
        }
        return result;
    }
    // interface
    @Override
    protected void onPostExecute(String s) {
        mFinishedInterface.onFinished(pulledBooks);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        mFinishedInterface.onProgress();

    }
}
