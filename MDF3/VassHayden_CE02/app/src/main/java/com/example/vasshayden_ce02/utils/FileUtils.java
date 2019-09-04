//Hayden Vass
//MDF3 - 1906
//FileUtils
package com.example.vasshayden_ce02.utils;

import android.net.Uri;

import com.example.vasshayden_ce02.R;

import java.util.Random;

public class FileUtils {

    public static Uri selectSongUri(int selector){
        Uri uriToReturn = null;
        String resroucePath = "android.resource://com.example.vasshayden_ce02/";
        switch(selector){
            case 0:
                uriToReturn = Uri.parse(resroucePath + R.raw.something_elated);
                break;
            case 1:
                uriToReturn = Uri.parse(resroucePath + R.raw.eagle);
                break;
            case 2:
                uriToReturn = Uri.parse(resroucePath + R.raw.dc);
                break;
        }
        return uriToReturn;
    }

    public static int setCurrentSong(String previousOrNext, int currentSong){
        String s = previousOrNext.toLowerCase();
        int numberToReturn = 0;
        switch (s){
            case "previous":
                if (currentSong == 0){
                    numberToReturn = 2;
                }else{
                    numberToReturn = currentSong - 1;
                }
                break;
            case "next":
                if (currentSong == 2){
                    numberToReturn = 0;
                }else{
                    numberToReturn = currentSong + 1;
                }
                break;
        }
        return numberToReturn;
    }

    public static int returnRandomUri(){
        Random r = new Random();
        return r.nextInt(3);
    }
}
