//hayden vass
//jav2 1905
//data contracts
package com.example.vasshayden_ce05;

import android.net.Uri;

public class DataContracts {

    //URI
    public static final String DATA_TABLE = "articles";
    public static final String URI_AUTHORITY = "com.fullsail.ce05.student.provider";
    //private static final String CONTENT_URI_STRING = "content://" + URI_AUTHORITY + "/";


    //INCOMING URI
    private static final String PROVIDER_TABLE = "books";
    private static final String URI_PROVIDER_AUTHORITY = "com.fullsail.ce05.provider";
    private static final String PROVIDER_URI_STRING = "content://" + URI_PROVIDER_AUTHORITY + "/";
    public static final Uri PROVIDER_CONTENT_URI = Uri.parse(PROVIDER_URI_STRING + PROVIDER_TABLE);


}
