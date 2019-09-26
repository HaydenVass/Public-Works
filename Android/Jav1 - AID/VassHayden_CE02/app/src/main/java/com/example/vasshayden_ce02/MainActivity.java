//Hayden Vass
// JAV1 - 1904
// CE02

package com.example.vasshayden_ce02;

import android.content.DialogInterface;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SEARCH";

    private final Random mRand = new Random();
    private static final int MAX_RAND_VALUE = 9;

    private EditText txt1;
    private EditText txt2;
    private EditText txt3;
    private EditText txt4;
    private EditText [] textFieldArray;
    private Integer [] randomInts;

    private Boolean isEmpty;

    private final @ColorInt
    int [] mColorIDs =
            {R.color.colorRed, R.color.colorGreen, R.color.colorBlue, R.color.colorInitial};
    private Toast mToast = null;
    private Integer guessCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isEmpty = false;
        findViewById(R.id.submit_btn).setOnClickListener(mClickListenerSubmit);
        txt1 = findViewById(R.id.editText_number1);
        txt2 = findViewById(R.id.editText_number2);
        txt3 = findViewById(R.id.editText_number3);
        txt4 = findViewById(R.id.editText_number4);
        textFieldArray = new EditText[]{txt1, txt2, txt3, txt4};
        guessCount = 5;
        randomInts = setRandomValues();

        for (Integer ranInt:randomInts) {
            Log.i(TAG, "onCreate: " + String.valueOf(ranInt));

        }
    }

    private final View.OnClickListener mClickListenerSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Validate

            if (txt1.getText().toString().isEmpty() || txt2.getText().toString().isEmpty() || txt3.getText().toString().isEmpty()
                    || txt4.getText().toString().isEmpty()){
                isEmpty = true;
            }
            // runs if all fields are filled
            if(!isEmpty){
                setColors(txt1, randomInts[0]);
                setColors(txt2, randomInts[1]);
                setColors(txt3, randomInts[2]);
                setColors(txt4, randomInts[3]);
                checkWinConditions();
            }else{
                if (mToast != null){
                    mToast.cancel();
                }
                //empty text fields toast
                mToast = Toast.makeText(MainActivity.this, R.string.toast_isEmpty, Toast.LENGTH_SHORT);
                isEmpty = false;
                mToast.show();

            }
        }
    };

    private void setColors(EditText eT, Integer randomInt){
        Integer userInput = Integer.parseInt(eT.getText().toString());
        if(userInput.equals(randomInt)){
            int colorValue = getColor(mColorIDs[1]);
            eT.setTextColor(colorValue);
        }else if (userInput < randomInt){
            int colorValue = getColor(mColorIDs[2]);
            eT.setTextColor(colorValue);
        }else if(userInput > randomInt){
            int colorValue = getColor(mColorIDs[0]);
            eT.setTextColor(colorValue);
        }
    }

    private void resetGame(){
        guessCount = 4;
        randomInts = setRandomValues();
        for (EditText et: textFieldArray) {
            et.setText("");
            int colorValue = getColor(mColorIDs[3]);
            et.setTextColor(colorValue);
        }
        txt1.requestFocus();
        for (Integer ranInt:randomInts) {
            Log.i(TAG, "onCreate: " + String.valueOf(ranInt));
        }
        if (mToast != null){
            mToast.cancel();
        }
    }

    private Integer[] setRandomValues(){
        int random1 = mRand.nextInt(MAX_RAND_VALUE);
        int random2 = mRand.nextInt(MAX_RAND_VALUE);
        int random3 = mRand.nextInt(MAX_RAND_VALUE);
        int random4 = mRand.nextInt(MAX_RAND_VALUE);
        return new Integer[]{random1, random2, random3, random4};
    }

    private boolean checkWinner(EditText eT, Integer randomInt){
        Integer userInput = Integer.parseInt(eT.getText().toString());
        return userInput.equals(randomInt);
    }

    private void checkWinConditions(){
        boolean check1 = checkWinner(txt1, randomInts[0]);
        boolean check2 = checkWinner(txt2, randomInts[1]);
        boolean check3 = checkWinner(txt3, randomInts[2]);
        boolean check4 = checkWinner(txt4, randomInts[3]);
        if(check1 && check2 && check3 && check4){
            showDialogs(getString(R.string.dialog_msg_win_title), getString(R.string.dialog_msg_win));
        }else if (guessCount >= 1){
            mToast = Toast.makeText(MainActivity.this, getString(R.string.guesst_counter, String.valueOf(guessCount)), Toast.LENGTH_SHORT);
            mToast.show();
            guessCount --;
        }else{
            showDialogs(getString(R.string.dialog_msg_lost_title), getString(R.string.dialog_msg_lost));
        }
    }

    private void showDialogs(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.dialog_pos_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
            }
        });
        builder.show();
    }
}
