//Hayden Vass
//MDF3 - 1906
//audio playback
package com.example.vasshayden_ce02;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.vasshayden_ce02.utils.FileUtils;
import com.example.vasshayden_ce02.utils.songMetaDataUtils;

import java.io.IOException;

public class AudioPlaybackService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private static final String TAG = "today";

    private static final int STATE_IDLE = 0;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_PREPARING = 2;
    private static final int STATE_PREPARED = 3;
    private static final int STATE_STARTED = 4;
    private static final int STATE_PAUSED = 5;
    private static final int STATE_STOPPED = 6;
    private static final int STATE_PLAYBACK_COMPLETED = 7;
    private static final int STATE_END = 8;

    private int mState;
    private MediaPlayer mPlayer;

    private static final int NOTIFICATION_ID = 0x0011;
    private static final String CHANNEL_ID = "AUDIO_CHANNEL";
    private static final String CHANNEL_NAME = "Audio Channel";
    private static final String NOTIFICATION_BUTTON_EXTRA_NEXT = "button_extra_next";
    private static final String NOTIFICATION_BUTTON_EXTRA_PREVIOUS = "button_extra_previous";

    private boolean isShuffling = false;
    private boolean isRepeating = false;
    private boolean shouldContinue = true;

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i(TAG, "onCompletion: finished");
        if(shouldContinue){
            if(isRepeating){
                Log.i(TAG, "onCompletion: is repeating");
                return;
            }
            this.playNext();
            sendBroadcast();
        }
    }

    public class AudioServiceBinder extends Binder {
        public AudioPlaybackService getService() {return AudioPlaybackService.this;}
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new AudioServiceBinder();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //play if prepared
        mState = STATE_PREPARED;
        mPlayer.start();
        if(isRepeating){
            mPlayer.setLooping(true);
        }
        if(isShuffling){
            MainActivity.currentSong = FileUtils.returnRandomUri();
        }
        mState = STATE_STARTED;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildNotificationChannel();

        mPlayer = new MediaPlayer();
        mState = STATE_IDLE;
        //set up listener for prepared
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPlayer.release();
        mState = STATE_END;
    }

    public void play(Uri songUri){
        shouldContinue = true;

        if(mState == STATE_PAUSED){

            mPlayer.start();
            if(isRepeating){
                mPlayer.setLooping(true);
            }
            mState = STATE_STARTED;

        }else if( mState != STATE_STARTED && mState != STATE_PREPARING){

            mPlayer.reset();
            mState = STATE_IDLE;

            try{
                // Set data source
                mPlayer.setDataSource(this, songUri);
                mState = STATE_INITIALIZED;
            }catch (IOException e){
                e.printStackTrace();
            }

            if(mState == STATE_INITIALIZED){
                // Start preparing asynchronously to move to STATE_PREPARING.
                mPlayer.prepareAsync();
                mState = STATE_PREPARING;
            }

            if(mState == STATE_STARTED){
                return;
            }

            Notification ongoing = buildNotification();
            startForeground(NOTIFICATION_ID, ongoing);
        }

    }

    public void pause(){
        if(mState == STATE_STARTED){
            mPlayer.pause();
            mState = STATE_PAUSED;
        }
    }

    public void stop(){
        if(mState == STATE_STARTED || mState == STATE_PAUSED || mState == STATE_PLAYBACK_COMPLETED){
            mPlayer.stop();
            mState = STATE_STOPPED;
            //on play back complete is one listener
            shouldContinue = false;

            //for notifications
            stopForeground(true);
        }
    }

    public void playNext(){
        Log.i(TAG, "playNext: ");
        this.stop();
        MainActivity.currentSong = FileUtils.setCurrentSong
                ("next", MainActivity.currentSong);
        this.play(FileUtils.selectSongUri(MainActivity.currentSong));
        //sendBroadcast();
    }


    public void playPrevious(){
        Log.i(TAG, "playPrevious: ");
        this.stop();
        MainActivity.currentSong = FileUtils.setCurrentSong
                ("previous", MainActivity.currentSong);
        this.play(FileUtils.selectSongUri(MainActivity.currentSong));
        //sendBroadcast();

    }

    //sets swtiches for shuffling and looping
    public void isShuffling(){
        isShuffling = !isShuffling;
        if(isShuffling){
            isRepeating = false;
        }
    }

    public void isLooping(){
        isRepeating = !isRepeating;
        if(isRepeating){
            isShuffling = false;
        }
        this.pause();
        mPlayer.setLooping(true);
        mPlayer.start();
    }

    public void setSeekBar(int progress){
        mPlayer.seekTo(progress);
    }




    //channel builder
    private void buildNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }

        }
    }

    //notifciation
    private Notification buildNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_music_note);
        builder.setContentTitle(getResources().getString(R.string.musicplaying));
        builder.setContentText(getResources().getString(R.string.expand_text));
        builder.setOngoing(true);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture
                (songMetaDataUtils.getAlbumArt(this, MainActivity.currentSong)));

        //intent to open application on button click
        Intent activityIntent = new Intent(this, MainActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activityIntent.putExtra("OPEN_NOTIFICATION", MainActivity.currentSong);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(
                this,
                0,
                activityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(activityPendingIntent);

        //intent for previous notification button
        Intent previousSongIntent = new Intent(this, AudioPlaybackService.class);
        previousSongIntent.putExtra(NOTIFICATION_BUTTON_EXTRA_PREVIOUS, "previous");
        PendingIntent previousPI = PendingIntent.getService(this,
                2,
                previousSongIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action previousAction = new
                NotificationCompat.Action((R.drawable.ic_music_note),
                "previous", previousPI);


        //pending intent / action for next button
        Intent notificationNextIntent = new Intent(this, AudioPlaybackService.class);
        notificationNextIntent.putExtra(NOTIFICATION_BUTTON_EXTRA_NEXT, "next");
        PendingIntent nextPI = PendingIntent.getService(
                this,
                1,
                notificationNextIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action nextAction = new
                NotificationCompat.Action(R.drawable.ic_music_note,
                "Next", nextPI);

        builder.addAction(previousAction);
        builder.addAction(nextAction);
        builder.setAutoCancel(false);

        return builder.build();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String nString = intent.getStringExtra(NOTIFICATION_BUTTON_EXTRA_NEXT);
        String pString = intent.getStringExtra(NOTIFICATION_BUTTON_EXTRA_PREVIOUS);
        if (nString != null){
            playNext();
        }else if (pString != null){
            playPrevious();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public MediaPlayer mediaPlayer(){
        return mPlayer;
    }

    public boolean getIsAppShuffling(){
        return isShuffling;
    }


    public boolean getIsAppLooping(){
        return isRepeating;
    }
    private void sendBroadcast() {
        Intent i = new Intent(MediaFragment.BROADCAST_ACTION);
        Context context = this;
        context.sendBroadcast(i);
    }

}
