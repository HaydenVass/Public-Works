//Hayden Vass
//Jav2 1905
//IOUTils
package com.example.vasshayden_ce03a.uitls;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.example.vasshayden_ce03a.MainActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class IOUtils {

    public static final String TAG = "output";

    //returns all files from a folder and converts them to URIs
    static public ArrayList<Uri> getUris(Context c, String imageFolder, String authority){
        File protectedStorage = c.getExternalFilesDir(imageFolder);
        ArrayList<Uri> urisToReturn = new ArrayList<>();
        File[] pulledFiles = Objects.requireNonNull(protectedStorage).listFiles();
        for (File pulledFile : pulledFiles) {
            urisToReturn.add(FileProvider.getUriForFile(c, authority, pulledFile));
        }
        return urisToReturn;
    }

    static public Uri getOutputUri(File file, Context c) {
        //File imagefile = IOUtils.generateFile(uniqueString, c);
        return FileProvider.getUriForFile(c, MainActivity.AUTHORITY, file);
    }

    static private File generateFile(String fileNameAppender, Context c){
        File protectedStorage = c.getExternalFilesDir(MainActivity.IMAGE_FOLDER);
        //create path to image file
        File imageFile = new File(protectedStorage, MainActivity.IMAGE_FILE+fileNameAppender);

        try{
            imageFile.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
        return imageFile;
    }
}
