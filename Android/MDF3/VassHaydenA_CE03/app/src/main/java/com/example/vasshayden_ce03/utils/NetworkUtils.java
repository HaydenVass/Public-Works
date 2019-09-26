package com.example.vasshayden_ce03.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class NetworkUtils {

    public static String getNetworkData(String apiURL) throws IOException {
        URL url = null;
        try {
            url = new URL(apiURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = (HttpURLConnection) Objects.requireNonNull(url).openConnection();
        connection.connect();
        InputStream is = connection.getInputStream();
        String data = IOUtils.toString(is);
        is.close();
        connection.disconnect();
        return data;
    }

    public static String getCurrentDateAndTime() {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        return simple.format(new Date());
    }
}
