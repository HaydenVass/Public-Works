package com.example.vasshayden_ce08.web;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.vasshayden_ce08.fragments.MainListFragment;

public class WvClient extends WebViewClient {
    private static final String TAG = "today";
    private final Context mContext;

    public WvClient(Context _context) {
        mContext = _context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i(TAG,"shouldOverrideUrlLoading() API 23-");
        return urlHandler(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.i(TAG,"shouldOverrideUrlLoading() API 24+");
        return urlHandler(view, request.getUrl().toString());
    }

    private boolean urlHandler(WebView _view, String _url) {
        Log.i(TAG,"urlHandler() url: \"" + _url + "\" from view ID: " + _view.getId());
        // Returning "false" means it is not handled, web view will show error screen
        //return false;
        Log.i(TAG, "urlHandler: " + _url.startsWith(WvInterface.FILE_SCHEME));
        if(_url.startsWith(WvInterface.FILE_SCHEME)){
            int idx = _url.indexOf(WvInterface.FILE_PATH);
            String data = _url.substring(idx + WvInterface.FILE_PATH.length());
            //send broadcast to main list fragment to launch details
            Intent i = new Intent(MainListFragment.BROADCAST_ACTION);
            i.putExtra("SELECTED_ITEM", data);
            mContext.sendBroadcast(i);
            return true;
        }
        return false;
    }

    @Override
    public void onReceivedError (WebView view,
                                 WebResourceRequest request,
                                 WebResourceError error) {
        Log.i(TAG,"onReceivedError() errorCode: " + error.getErrorCode() +
                " description: \"" + error.getDescription() + "\"");
    }


}
