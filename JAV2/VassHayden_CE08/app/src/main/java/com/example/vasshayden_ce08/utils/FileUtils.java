package com.example.vasshayden_ce08.utils;

import android.content.Context;

import com.example.vasshayden_ce08.objects.Student;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FileUtils {

    public static void SaveSerializable(String fileName, Context c, ArrayList<Student> studentsList){
        try{
            FileOutputStream fos = c.openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(studentsList);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    //loads list from internal memory
    public static ArrayList<Student> LoadSerializable(String fileName, Context c){
        ArrayList<Student> articlesToSend = new ArrayList<>();
        try{
            FileInputStream fis = c.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            articlesToSend = (ArrayList<Student>)ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return articlesToSend;
    }

}
