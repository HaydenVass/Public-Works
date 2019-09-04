//Hayden Vass
//Jav21905
//on boot reciever
package com.example.vasshayden_ce07.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.vasshayden_ce07.Utils.JobUtils;
import com.example.vasshayden_ce07.fragments.MainListFragment;

public class OnBootReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        //start job on boot
        JobUtils.startJobService(MainListFragment.JOB_ID, true, "pup", context);

    }
}
