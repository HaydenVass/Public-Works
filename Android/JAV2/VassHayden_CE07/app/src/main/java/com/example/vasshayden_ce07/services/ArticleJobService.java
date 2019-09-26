//Hayden Vass
//Jav21905
//ArticleJobService
package com.example.vasshayden_ce07.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.vasshayden_ce07.MainActivity;
import com.example.vasshayden_ce07.R;
import com.example.vasshayden_ce07.Utils.NetworkUtils;
import com.example.vasshayden_ce07.async_task.GetArticles;
import com.example.vasshayden_ce07.objects.Article;

public class ArticleJobService extends JobService implements GetArticles.OnFinished {

    public static final String EXTRAS_STRING = " ";
    private static final boolean SHOULD_RESCHEDULE = true;
    public static final String SAVE_ID = "SAVED_ITEM";


    @Override
    public boolean onStartJob(JobParameters params) {
        //if connected - async task starts (GetArticles)
        if(NetworkUtils.isConnected(this)){
            GetArticles puppyTask = new GetArticles
                    ("https://www.reddit.com/r/puppies/hot.json", this);
            puppyTask.execute();
        }
        jobFinished(params, SHOULD_RESCHEDULE);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    //article from async task call back - present notification
    @Override
    public void SendArticleBack(Article pulledArticle) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder (this, MainActivity.CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_pets);
        builder.setContentTitle(pulledArticle.getArticleName());
        builder.setContentText(pulledArticle.getAuthor());

        //expanded features
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.setBigContentTitle(pulledArticle.getArticleName());
        style.setSummaryText(pulledArticle.getAuthor());
        style.bigText("Article: " + pulledArticle.getArticleName() + "\n"+ "Author: " +
                pulledArticle.getAuthor() + "\n"+ "Up Votes: " +
                pulledArticle.getUpVotes() + "\n" + "Score: " + pulledArticle.getScore() +
                "\n" + "Url: " + pulledArticle.getArticleUrl());
                builder.setStyle(style);

        //pending intent - takes user to article on click
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(pulledArticle.getArticleUrl()));
        PendingIntent pi = PendingIntent.getActivity
                (this, 0, i,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(pi);

        // saves item on button click - start services for ArticleIntentService
        Intent buttonIntent = new Intent(this, ArticleIntentService.class);
        buttonIntent.putExtra(SAVE_ID, pulledArticle);
        PendingIntent buttonPI = PendingIntent.getService(this, 0, buttonIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        //make action
        NotificationCompat.Action action = new
                NotificationCompat.Action(R.drawable.ic_add_circle,
                "Save", buttonPI);
                 builder.addAction(action);
                 builder.setAutoCancel(true);

        NotificationManager mgr =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (mgr != null){
            mgr.notify(MainActivity.STANDARD_NOTIFICATION, builder.build());
        }

    }
}
