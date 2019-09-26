package com.example.vasshayden_ce01.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.example.vasshayden_ce01.objects.SavedLocation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FileUtils {

    private static final String TAG = "today";


    public static void SaveSerializable(String fileName, Context c, SavedLocation savedLocation){
        ArrayList<SavedLocation> sl;
        sl = LoadSerializable(fileName, c);
        sl.add(savedLocation);
        try{
            FileOutputStream fos = c.openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sl);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void SaveSerializableList(String fileName, Context c, ArrayList<SavedLocation> locations){
        try{
            FileOutputStream fos = c.openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(locations);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //loads list from internal memory
    public static ArrayList<SavedLocation> LoadSerializable(String fileName, Context c){
        ArrayList<SavedLocation> allSavedLocations = new ArrayList<>();
        try{
            FileInputStream fis = c.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            allSavedLocations = (ArrayList<SavedLocation>)ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return allSavedLocations;
    }


    //method from android documentation
    public static File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        Log.i(TAG, "getPublicAlbumStorageDir: "+ file.getAbsolutePath());
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }

    //saves bitmap
    public static void saveBitmapToExternalStorage(Bitmap photo, File file){
        FileOutputStream outputStream;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            outputStream = new FileOutputStream(file, true);
            outputStream.write(byteArray);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
