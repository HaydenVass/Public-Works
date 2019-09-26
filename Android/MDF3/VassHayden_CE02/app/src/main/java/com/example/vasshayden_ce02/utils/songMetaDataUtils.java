//Hayden Vass
//MDF3 - 1906
//mp3 utils
package com.example.vasshayden_ce02.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

public class songMetaDataUtils {


    public static String getSongTitle(Context context, int selectedSongIndicator){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context, FileUtils.selectSongUri(selectedSongIndicator));
        String songTitle = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        mmr.release();
        return songTitle;
    }

    public static Bitmap getAlbumArt(Context context, int selectedSongIndicator){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context, FileUtils.selectSongUri(selectedSongIndicator));
        byte [] data = mmr.getEmbeddedPicture();
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
