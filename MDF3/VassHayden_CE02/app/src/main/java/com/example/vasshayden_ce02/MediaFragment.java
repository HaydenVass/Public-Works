//Hayden Vass
//MDF3 - 1906
//media fragment
package com.example.vasshayden_ce02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.vasshayden_ce02.utils.songMetaDataUtils;

import java.util.Objects;

public class MediaFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "ControlFragment.TAG";
    private Switch loopSwitch;
    private Switch randomSwitch;
    private SeekBar mSeekbar;


    public static final String BROADCAST_ACTION =
            "com.example.vasshayden_ce02.Fragments.BROADCAST_ACTION";
    private final updateReceiver reciever = new updateReceiver();
    
    public MediaFragment() {
    }


    //public static MediaFragment newInstance() {
//        return new MediaFragment();
//    }

    public static MediaFragment newInstance(boolean isRandom, boolean isLooping) {
        Bundle args = new Bundle();
        args.putBoolean("isRandom", isRandom);
        args.putBoolean("isLooping", isLooping);
        MediaFragment fragment = new MediaFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public interface PlaybackCommandListener{
        void play();
        void pause();
        void stop();
        void skip();
        void previous();
        void isShuffling();
        void isLooping();
        void seeking(SeekBar sb);
    }


    private PlaybackCommandListener mListener;
    private TextView albumTitleTextView;
    private ImageView albumArt;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof PlaybackCommandListener){
            mListener = (PlaybackCommandListener)context;
        }
    }

    @Override
    public void onClick(View view) {
        if(mListener == null) {
            return;
        }
        if(view.getId() == R.id.button_play){
            //play with passed URI
            mListener.play();
            //setAlbumDetails();

        }else if(view.getId() == R.id.button_pause){
            mListener.pause();

        }else if(view.getId() == R.id.button_stop){
            mListener.stop();


        }else if(view.getId() == R.id.button_next){
           mListener.skip();
           setAlbumDetails();


        }else if(view.getId() == R.id.button_previous){
            mListener.previous();
            setAlbumDetails();


        }else if(view.getId() == R.id.switch_random){
            mListener.isShuffling();
            loopSwitch.setChecked(false);


        }else if(view.getId() == R.id.switch_looping){
            mListener.isLooping();
            randomSwitch.setChecked(false);
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.media_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSeekbar = Objects.requireNonNull(getActivity()).findViewById(R.id.seekBar_song);
        albumTitleTextView = getActivity().findViewById(R.id.textView_songtitle);
        albumArt = getActivity().findViewById(R.id.imageView_albumart);
        randomSwitch = getActivity().findViewById(R.id.switch_random);
        loopSwitch = getActivity().findViewById(R.id.switch_looping);
        mListener.seeking(mSeekbar);

        setAlbumDetails();
        loopSwitch.setChecked(Objects.requireNonNull(getArguments()).getBoolean("isLooping"));
        randomSwitch.setChecked(getArguments().getBoolean("isRandom"));


        View root = getView();
        if(root != null) {
            root.findViewById(R.id.button_play).setOnClickListener(this);
            root.findViewById(R.id.button_stop).setOnClickListener(this);
            root.findViewById(R.id.button_pause).setOnClickListener(this);
            root.findViewById(R.id.button_next).setOnClickListener(this);
            root.findViewById(R.id.button_previous).setOnClickListener(this);
            root.findViewById(R.id.switch_random).setOnClickListener(this);
            root.findViewById(R.id.switch_looping).setOnClickListener(this);
        }

    }

    private void setAlbumDetails(){
        albumTitleTextView.setText(songMetaDataUtils.
                getSongTitle(getContext(), MainActivity.currentSong));

        albumArt.setImageBitmap(songMetaDataUtils.getAlbumArt(getContext(),
                MainActivity.currentSong));

        mSeekbar.setProgress(0);
    }

    //reciever based methods - updats UI from service
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION);
        Objects.requireNonNull(getActivity()).registerReceiver(reciever, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getActivity()).unregisterReceiver(reciever);
    }
    //nested reciever class
    class updateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null){
                //updates ui while app is open
                setAlbumDetails();

            }
        }
    }

}

