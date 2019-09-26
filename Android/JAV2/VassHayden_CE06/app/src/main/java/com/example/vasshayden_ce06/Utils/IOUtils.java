
//Hayden Vass
// Jav2 - 1905
//ioutils
package com.example.vasshayden_ce06.Utils;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class IOUtils {

    //returns all files from a folder and converts them to URIs
    static public ArrayList<Uri> getUris(Context c){

        File protectedStorage = c.getExternalFilesDir(Contracts.IMAGE_FOLDER);
        ArrayList<Uri> urisToReturn = new ArrayList<>();

        File[] pulledFiles = Objects.requireNonNull(protectedStorage).listFiles();
        for (File pulledFile : pulledFiles) {
            urisToReturn.add(FileProvider.getUriForFile(c, Contracts.AUTHORITY, pulledFile));
        }
        return urisToReturn;
    }

    static public File generateFile(Context c, String fileName){
        File protectedStorage = c.getExternalFilesDir(Contracts.IMAGE_FOLDER);
        //create path to image file
        File imageFile = new File(protectedStorage, fileName);
        if(imageFile.exists()){
            //if file does exist, returns out of the method
            return null;
        }else{
            //if the file doesnt exist, adds it to device storeage and returns the file to be
            //wrote to
            try{
                imageFile.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return imageFile;
    }
}