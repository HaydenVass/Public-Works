//Hayden Vass
//Jav 2 - 1905
//Main
package com.example.vasshayden_ce04;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_ce04.fragments.MainListFragment;
import com.example.vasshayden_ce04.utils.ContactUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String PERMISSION_PROVIDER = "android.permission.READ_CONTACTS";
    private static final int PERMISSION_RESULT_CODE = 0x00001001;

    private ArrayList<String> uniqueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uniqueId = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    private void checkPermissions(){
        //grants access to contacxts
        int resultCode = ContextCompat.checkSelfPermission(this, PERMISSION_PROVIDER);
        if(resultCode != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {PERMISSION_PROVIDER}, PERMISSION_RESULT_CODE);
            //check if the user pressed yes or no
        }else{
            //perform query
            uniqueId = ContactUtils.getAllIds(this);
            if(uniqueId.size() != 0){
                //if there are contact numbers in the contacts, app continues by loading a fragment,
                //with all pertaining data
                getSupportFragmentManager().beginTransaction().replace(R.id.list_container,
                        MainListFragment.newInstance(uniqueId)).commit();
            }else{
                //deletes all fragments if user deletes all contacts
                for (Fragment fragment:getSupportFragmentManager().getFragments()) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_RESULT_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                uniqueId = ContactUtils.getAllIds(this);
                if (uniqueId.size() != 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.list_container,
                            MainListFragment.newInstance(uniqueId)).commit();
                }
            }
        }
    }
}
