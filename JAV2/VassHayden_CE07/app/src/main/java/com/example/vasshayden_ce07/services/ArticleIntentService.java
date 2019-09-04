//Hayden Vass
//Jav21905
//ArticleIntentService
package com.example.vasshayden_ce07.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.example.vasshayden_ce07.fragments.MainListFragment;
import com.example.vasshayden_ce07.objects.Article;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class ArticleIntentService extends IntentService {

    public ArticleIntentService() {
        super("ArticleIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // save service
        Article savedArticle = (Article) intent.getSerializableExtra(ArticleJobService.SAVE_ID);
        try{
            FileOutputStream fos = openFileOutput("Saved.txt", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(savedArticle);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        //once article has been saved - broadcast will be sent and recieved by list fragment
        sendBroadcast();
    }

    private void sendBroadcast() {
        Intent i = new Intent(MainListFragment.BROADCAST_ACTION);
        Context context = this;
        context.sendBroadcast(i);
    }
}
