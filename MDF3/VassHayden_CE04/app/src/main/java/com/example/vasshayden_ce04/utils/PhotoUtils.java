package com.example.vasshayden_ce04.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.ArrayList;

public class PhotoUtils {
    private static final String AUTHORITY = "com.example.android.fileprovider";



    //used for widgets
    public static ArrayList<Uri> getImagesPath(Context context) {
        ArrayList<Uri> allPaths = new ArrayList<>();
        String imagePath;
        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] proj = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        Cursor c = context.getContentResolver().query(uri, proj, null,
                null, null);

        assert c != null;
        int cData = c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (c.moveToNext()) {
            imagePath = c.getString(cData);
            Uri imgUri = Uri.fromFile(new File(imagePath));

            allPaths.add(imgUri);
        }

        c.close();
        return allPaths;
    }

    //used for file provider - main application
    public static ArrayList<Uri> getContntImagesPath(Context context) {
        ArrayList<Uri> allPaths = new ArrayList<>();
        String imagePath;
        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] proj = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        Cursor c = context.getContentResolver().query(uri, proj, null,
                null, null);

        assert c != null;
        int cData = c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (c.moveToNext()) {
            imagePath = c.getString(cData);
            Uri photoUri = FileProvider.getUriForFile(context,AUTHORITY, new File(imagePath));

            allPaths.add(photoUri);
        }

        c.close();
        return allPaths;
    }

}
