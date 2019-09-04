//Hayden Vass
// CE06 - Jav1 1904
//VassHayden_CE06
package com.example.haydenvass_ce06;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CounterAsync.OnFinished {

    //buttons
    private Button startButton;
    private Button stopButton;
    //fields
    private EditText minutes_et;
    private EditText seconds_et;
    private TextView timerText;

    private CounterAsync countTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // countdown view
        setContentView(R.layout.activity_main);
        timerText = findViewById(R.id.timerText);
        timerText.setVisibility(View.GONE);
        //buttons
        startButton = findViewById(R.id.start);
        stopButton = findViewById(R.id.stop);
        //Edit text - colon
        minutes_et = findViewById(R.id.minutes);
        seconds_et = findViewById(R.id.seconds);

        //on click listeners
        startButton.setOnClickListener(startBtnPressed);
        stopButton.setOnClickListener(stopBtnPressed);

        //disable stop button
        stopButton.setEnabled(false);

    }

    private final View.OnClickListener startBtnPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //validates user input
            if(minutes_et.getText().toString().isEmpty() || seconds_et.getText().toString().isEmpty() ||
                    minutes_et.getText().toString().equals("0") && seconds_et.getText().toString().equals("0") ||
                    minutes_et.getText().toString().contains("00") && seconds_et.getText().toString().contains("00")){
                presentToast(getString(R.string.valid_time));
                return;
            }
            //show and hide elements
            toggleInputOff();
            countTask = new CounterAsync(MainActivity.this);
            countTask.execute(minutes_et.getText().toString(), seconds_et.getText().toString());
            String startText = minutes_et.getText().toString() + ":" + seconds_et.getText().toString();
            timerText.setText(startText);
            presentToast("Timer Started.");
        }
    };


    private final View.OnClickListener stopBtnPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleInputOn();
            // shows edit text and cancels async task
            if (countTask != null){
                countTask.cancel(true);
            }
        }
    };

    private void presentToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onFinished() {
        // flips edit text back on
        toggleInputOn();
        displayDialog(getString(R.string.timer_finished), "");
    }

    public void onCancelled(Float result){
        toggleInputOn();
        //converts time to minutes and seconds
        int minutes = Math.round((result / 60) % 60);
        int seconds = Math.round(result %60);
        // display text for cancel dialog
        String displayText = getString(R.string.elapsed_time, String.valueOf(minutes), String.valueOf(seconds));
        displayDialog(getString(R.string.timer_cancelled), displayText);

    }

    public void onProgress(Integer ... values){
        String time;
        int seconds = values[0] % 60;
        int minutes = (values[0] /60) % 60;
        if (seconds < 10){
            time = String.valueOf(minutes) +":0" + String.valueOf(seconds);
        }else{
            time = String.valueOf(minutes) +":" + String.valueOf(seconds);
        }
        if (minutes < 10){
            time = "0" + time;
        }
        timerText.setText(time);
    }

    private void displayDialog(String dialogTitle, String dialogMessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(dialogTitle);
        builder.setCancelable(false);
        builder.setMessage(dialogMessage);
        builder.setPositiveButton(R.string.close, null);
        builder.show();
    }

    private void toggleInputOn(){
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        timerText.setVisibility(View.GONE);
        minutes_et.setVisibility(View.VISIBLE);
        seconds_et.setVisibility(View.VISIBLE);
        minutes_et.setText(getString(R.string.zerozero));
        seconds_et.setText(getString(R.string.zerozero));
    }

    private void toggleInputOff(){
        timerText.setText("");
        timerText.setVisibility(View.VISIBLE);
        minutes_et.setVisibility(View.GONE);
        seconds_et.setVisibility(View.GONE);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }
}
