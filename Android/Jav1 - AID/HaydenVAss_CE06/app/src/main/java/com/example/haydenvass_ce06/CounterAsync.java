package com.example.haydenvass_ce06;

import android.os.AsyncTask;

class CounterAsync extends AsyncTask <String, Integer, Float> {


    final private OnFinished mFinishedInterface;

    // An inner-interface for the user of this class to be notified when the task is done
    interface OnFinished {
        void onFinished();
        void onCancelled(Float result);
        void onProgress(Integer...values);
    }

    CounterAsync(OnFinished _finished) {
        mFinishedInterface = _finished;
    }

    @Override
    protected void onPreExecute() { }

    @Override
    protected Float doInBackground(String... strings) {
        // process parameter passed to the execute method
        if(strings == null || strings.length <= 0 || strings[0].trim().isEmpty()){
            return 0.0f;
        }

        // convert minutes to seconds
        Integer minutes = Integer.parseInt(strings[0]) * 60;
        Integer seconds = Integer.parseInt(strings[1]);
        Integer totalTime = minutes + seconds;

        long startTime = System.currentTimeMillis();
        long checkTime = System.currentTimeMillis();

        while(totalTime >= 0 && !isCancelled()){
            long SLEEP = 450;
            //checks to see if at least 450 ms has passed to improve consistency
            //if it does it edits the total time and passes that data to the main thread
            if( checkTime + SLEEP < System.currentTimeMillis()){
                publishProgress(totalTime);
                try{
                    Thread.sleep(SLEEP);
                    checkTime = System.currentTimeMillis();
                }catch (InterruptedException e ){
                    e.printStackTrace();
                }
                totalTime --;
            }
        }
        // Return real elapsed time
        return ((System.currentTimeMillis() - startTime))/1000f;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        mFinishedInterface.onProgress(values);
    }

    @Override
    protected void onPostExecute(Float aFloat) {
        // Invoke interface
        mFinishedInterface.onFinished();
    }

    @Override
    protected void onCancelled(Float aFloat) {
        // Invoke interface
        mFinishedInterface.onCancelled(aFloat);
    }
}
