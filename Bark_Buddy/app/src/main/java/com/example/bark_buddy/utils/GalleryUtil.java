package com.example.bark_buddy.utils;

import android.content.Intent;

public class GalleryUtil {

    public static Intent openGallery(){
        Intent getImageIntent = new Intent(Intent.ACTION_PICK);
        getImageIntent .setType("image/*");
        return getImageIntent;
    }

}
