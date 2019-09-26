//Hayden Vass
//Jav2 - 1905
//main preferences fragment
package com.example.vasshayden_ce02.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.vasshayden_ce02.R;
import com.example.vasshayden_ce02.utils.DatabaseHelper;

public class MainPreferencesFragment extends PreferenceFragmentCompat {

    private static final String CLICK_PREF_DELETE = "delete_preference";
    private static final String CLICK_PREF_LIST = "list_preference";

    private Activity mActivity;
    private static final String TAG = "today";

    public MainPreferencesFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference,rootKey);
    }
    

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        Preference deletePref = findPreference(CLICK_PREF_DELETE);

        if(deletePref != null){
            deletePref.setOnPreferenceClickListener(deleteClickListener);
        }
        Preference listPref = findPreference(CLICK_PREF_LIST);
        if(listPref != null){
            listPref.setOnPreferenceChangeListener(listChange);
        }
    }
    private final Preference.OnPreferenceClickListener deleteClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            showDeleteDialog();
            return false;
        }
    };
    
    private final Preference.OnPreferenceChangeListener listChange = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            Log.i(TAG,"Key: " + preference.getKey() + " NEW Value: " + o);
            return true;
        }
    };

    // sets list prefs to other parts of theapplication - details fragment
    public static String getDatePrefrence(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("list_preference", null);
    }

    //dialog for deleting all uerw
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.delete));
        builder.setMessage(getResources().getString(R.string.Are_you_sure));
        builder.setPositiveButton(getResources().getString(R.string.cancel), null);
        builder.setNegativeButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper dbh = DatabaseHelper.getInstance(mActivity);
                dbh.deleteAll();
                Toast.makeText(getContext(), getResources().getString(R.string.alldeleted), Toast.LENGTH_LONG).show();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
