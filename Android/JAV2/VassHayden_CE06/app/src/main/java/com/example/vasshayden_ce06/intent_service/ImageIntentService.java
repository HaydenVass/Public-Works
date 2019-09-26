//Hayden Vass
// Jav2 - 1905
//image intent service

package com.example.vasshayden_ce06.intent_service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.vasshayden_ce06.Utils.IOUtils;
import com.example.vasshayden_ce06.Utils.NetworkUtils;
import com.example.vasshayden_ce06.fragments.MainListFragments;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageIntentService extends IntentService {

    public static final String TAG = "today";
    private final String[] IMAGES = {
            "Df9sV7x.jpg", "nqnegVs.jpg", "JDCG1tP.jpg",
            "tUvlwvB.jpg", "2bTEbC5.jpg", "Jnqn9NJ.jpg",
            "xd2M3FF.jpg", "atWe0me.jpg", "UJROzhm.jpg",
            "4lEPonM.jpg", "vxvaFmR.jpg", "NDPbJfV.jpg",
            "ZPdoCbQ.jpg", "SX6hzar.jpg", "YDNldPb.jpg",
            "iy1FvVh.jpg", "OFKPzpB.jpg", "P0RMPwI.jpg",
            "lKrCKtM.jpg", "eXvZwlw.jpg", "zFQ6TwY.jpg",
            "mTY6vrd.jpg", "QocIraL.jpg", "VYZGZnk.jpg",
            "RVzjXTW.jpg", "1CUQgRO.jpg", "GSZbb2d.jpg",
            "IvMKTro.jpg", "oGzBLC0.jpg", "55VipC6.jpg" };

    public ImageIntentService() {
        super("ImageIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onStart( Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        byte [] bytes = null;
        Log.i(TAG, "onHandleIntent: ");
        for (String path : IMAGES) {
            try {
                String URL_BASE = "https://i.imgur.com/";
                bytes = NetworkUtils.getNetworkData(URL_BASE +path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            File imageFile = IOUtils.generateFile(this, path);
            if(imageFile != null){
                try {
                    //if the file doesnt already exist, the data gets wrote to the file
                    //and a broadcast is sent saying a new file exist
                    FileOutputStream fos = new FileOutputStream(imageFile);
                    fos.write(bytes);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            sendBroadcast();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    private void sendBroadcast() {
        Intent i = new Intent(MainListFragments.BROADCAST_ACTION);
        Context context = this;
        context.sendBroadcast(i);
    }
}
