//Hayden Vass
//JAV2 - 1905
//NetworkUtils

package com.example.vasshaydewn_ce01;

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


class NetworkUtils {

    static boolean isConnected(Context _context) {
        ConnectivityManager mgr = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(mgr != null) {
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if(info != null) {
                return info.isConnected();
            }
        }
        return false;
    }

    static String getNetworkData(String apiURL) throws IOException {
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
}
