//Hayden Vass
//MDF3 - 1906
//main activity
package com.example.vasshayden_ce02;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;

import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;


import com.example.vasshayden_ce02.utils.FileUtils;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ServiceConnection,
MediaFragment.PlaybackCommandListener{

    public static int currentSong = 0;
    private Timer timer;

    private static final String TAG = "today";
    private AudioPlaybackService mService;
    private boolean mBound;
    private int mProgress;
    private SeekBar sBar;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //per this error - apply is called in the method at the bottom.
        editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        replaceFrag(false, false);
        currentSong = 0;


        timer = new Timer();
        currentSong = prefs.getInt("currentSong", 0);
        Intent i = getIntent();
        int openFromNotification = i.getIntExtra("OPEN_NOTIFICATION", -1);
        if(openFromNotification != -1){
            //keeps track of what song was playing when the app was closed
            currentSong = openFromNotification;
        }

    }

    @Override
    protected void onDestroy() {
        updateSavedPrefs();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBound = false;
        Intent serviceIntent = new Intent(this, AudioPlaybackService.class);
        bindService(serviceIntent, this, BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(this);
        mBound  = false;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder iBinder) {
        AudioPlaybackService.AudioServiceBinder binder =
                (AudioPlaybackService.AudioServiceBinder)iBinder;

        mService = binder.getService();
        mBound = true;
        if (mService != null){
            //used to re indicate if loop or shuffle is still on -- tried to get the scrub bar synced
            //but ran out of time. If you press the play button while opening the app back up the scrub bar
            // will move to the approprite place. I couldnt figure out why or get a work around in before
            //having to turn in the assignment
            replaceFrag(mService.getIsAppLooping(), mService.getIsAppShuffling());
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mBound = false;
    }

    @Override
    public void play() {
        if(mBound){
            mService.play(FileUtils.selectSongUri(currentSong));
            Intent i = new Intent(this, AudioPlaybackService.class);
            startService(i);
        }
        startTimer();
    }


    @Override
    public void pause() {
        if(mBound){
            mService.pause();
        }
    }

    @Override
    public void stop() {
        if(mBound){
            mService.stop();
            Intent i = new Intent(this, AudioPlaybackService.class);
            stopService(i);
        }

    }

    @Override
    public void skip() {
        mService.playNext();
        startTimer();
    }

    @Override
    public void previous() {
        mService.playPrevious();
        startTimer();
    }

    // switches for turning on / off shuffle
    @Override
    public void isShuffling() {
        mService.isShuffling();
    }

    @Override
    public void isLooping() {
        mService.isLooping();
    }

    //seek bar method - used for moving song to particular place
    @Override
    public void seeking(final SeekBar sb) {
        sBar = sb;
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            // resume timer ?? + progress?
                mService.setSeekBar(mProgress);

            }
        });
    }


    //sets timer for seek bar
    private void startTimer(){
        sBar.setMax(mService.mediaPlayer().getDuration());
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sBar.setProgress(mService.mediaPlayer().getCurrentPosition());

            }
        },0,1000);
    }



    //saves tertiary data for loading when the app re opens
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentSong", currentSong);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentSong = savedInstanceState.getInt("currentSong");
        Log.i(TAG, "onRestoreInstanceState: " + currentSong);
    }

    private void updateSavedPrefs(){
        editor.putInt("currentSong", currentSong);
        editor.putInt("progress", mProgress);
        editor.putInt("previousSongLength", mService.mediaPlayer().getDuration());
        editor.apply();

    }

    private void replaceFrag(boolean looping, boolean random){
        MediaFragment frag = MediaFragment.newInstance(random, looping);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, frag, MediaFragment.TAG)
                .commit();
    }

}
