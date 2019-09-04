//Hayden Vass
// Jav2 - 1905
//network utils

package com.example.vasshayden_ce06.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class NetworkUtils {

   public static boolean isConnected(Context _context) {
        ConnectivityManager mgr = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(mgr != null) {
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if(info != null) {
                return info.isConnected();
            }
        }
        return false;
    }


    public static byte[] getNetworkData(String apiURL) throws IOException {
        URL url = null;
        try {
            url = new URL(apiURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = (HttpURLConnection) Objects.requireNonNull(url).openConnection();
        connection.connect();
        InputStream is = connection.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);
        is.close();
        connection.disconnect();
        return bytes;
    }
}
