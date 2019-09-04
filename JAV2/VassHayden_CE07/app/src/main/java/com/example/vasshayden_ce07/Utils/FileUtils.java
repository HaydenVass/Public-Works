package com.example.vasshayden_ce07.Utils;

import android.app.Activity;

import com.example.vasshayden_ce07.objects.Article;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FileUtils {

    public static Article saveArticle(Activity a){
        FileInputStream fis;
        Article articleToReturn = null;
        try {
            fis = a.openFileInput("Saved.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            articleToReturn = (Article)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return articleToReturn;
    }

    public static void SaveSerializableArray(String key, ArrayList<Article> _selectedArticle, Activity a){
        try{
            FileOutputStream fos = a.openFileOutput(key, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_selectedArticle);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //loads list from internal memory
    public static ArrayList<Article> LoadSerializableArray(String key, Activity a){
        ArrayList<Article> articlesToSend = new ArrayList<>();
        try{
            FileInputStream fis = a.openFileInput(key);
            ObjectInputStream ois = new ObjectInputStream(fis);
            articlesToSend = (ArrayList<Article>)ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return articlesToSend;
    }
}
