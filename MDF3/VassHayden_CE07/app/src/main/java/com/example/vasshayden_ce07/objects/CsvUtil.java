package com.example.vasshayden_ce07.objects;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class CsvUtil {

    public static ArrayList<HiddenTreasure> getData(Context c, int rawResource){
        ArrayList<HiddenTreasure> treasures = new ArrayList<>();
        InputStream is = c.getResources().openRawResource(rawResource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                treasures.add(new HiddenTreasure(data[0], Integer.valueOf(data[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return treasures;
    }
}
