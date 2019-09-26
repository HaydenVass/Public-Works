//Hayden Vass
//Jav2 1905
//MainActivity
package com.example.vasshayden_ce03a;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_ce03a.fragments.GridFragment;


public class MainActivity extends AppCompatActivity implements GridFragment.forResult {
    public static final int REQUEST_TAKE_PICTURE = 0x01001;
    public static final String AUTHORITY = "com.example.android.fileprovider";
    public static final String IMAGE_FOLDER = "images";
    public static final String  IMAGE_FILE = "my_picture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //checks bundle contents to see if the application was launched form intent or not
        int launchId = 0;
        if(getIntent().getExtras() != null){ launchId = 1; }
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentcontainer,
                GridFragment.newInstance(launchId)).commit();
    }


    //create interface that sends back UID of selected picture and if the bundle isnt null
    //finish the activity which will fire the onresult event in app b
    @Override
    public void closeIfLaunchedFromB(Uri selectedUri) {
        if(getIntent() != null){
            if(getIntent().getExtras() != null){
                String uriString = selectedUri.toString();
                Intent returnIntent = new Intent();
                //grant permission to application B to view protected fkles
                grantUriPermission("com.example.vasshayden_ce03b",
                        selectedUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                returnIntent.putExtra(Intent.EXTRA_TEXT, uriString);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        }
    }
}
