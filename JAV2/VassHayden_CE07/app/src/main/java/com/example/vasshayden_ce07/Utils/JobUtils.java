package com.example.vasshayden_ce07.Utils;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.vasshayden_ce07.services.ArticleJobService;

public class JobUtils {
    private static final String TAG = "today";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void startJobService(int _jobID, boolean _isPeriodic, String _extra, Context a) {

        PersistableBundle extras = new PersistableBundle();
        extras.putString(ArticleJobService.EXTRAS_STRING, _extra);

        ComponentName componentName = new ComponentName(a, ArticleJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(_jobID, componentName);
        builder.setMinimumLatency(0);
        builder.setOverrideDeadline(0);
        builder.setExtras(extras);

        if (_isPeriodic) {
            Log.i(TAG, "startJobService: is periodic ");
            builder.setPeriodic(900000L,14);
        }
        JobInfo jobInfo = builder.build();

        int resultCode = 0;
        JobScheduler jobScheduler = (JobScheduler) a.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null) {
            resultCode = jobScheduler.schedule(jobInfo);
        }

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.i(TAG, "startJobService: ID " + jobInfo.getId());
        } else {
            Log.i(TAG, "startJobService: Job not scheduled...");
        }
    }
}
