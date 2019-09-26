package com.example.vasshayden_ce08.utils;

import android.content.Context;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.vasshayden_ce08.MainActivity;
import com.example.vasshayden_ce08.objects.Student;
import com.example.vasshayden_ce08.web.WvClient;
import com.example.vasshayden_ce08.web.WvInterface;

public class WebUtils {

    private static final String TAG = "today";

    public static void webViewLoad(WebView mWebView, String _uri) {
        if(mWebView == null || _uri==null || _uri.trim().isEmpty()) {
            return;
        }
        for (String schema : MainActivity.mSchemas) {
            if (_uri.startsWith(schema)) {
                //  Load URL
                mWebView.loadUrl(_uri);
                return;
            }
        }
    }

    public static void setupWebView(WebView mWebView, Context c, Student student) {
        //peram = find view by id
        if(mWebView == null) {
            return;
        }
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        // add client
        mWebView.setWebViewClient(new WvClient(c));
        mWebView.addJavascriptInterface(new WvInterface(c, student), "Android");
        //console message
        mWebView.setWebChromeClient(new WebChromeClient(){
            public boolean onConsoleMessage(ConsoleMessage cm){
                Log.d(TAG, "onConsoleMessage: " + "--- from line " +
                        cm.lineNumber() + " of " + cm.sourceId());
                return true;
            }
        });
    }
}
