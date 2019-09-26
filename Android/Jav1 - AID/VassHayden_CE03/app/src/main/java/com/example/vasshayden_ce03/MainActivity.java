// Hayden Vass
// 1904- Jav1
// CE03

package com.example.vasshayden_ce03;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    private NumberPicker numPicker;
    private EditText userInput;
    private TextView avg_tV;
    private TextView median_tV;
    private ArrayList<String> usersWords;
    private DecimalFormat decFormat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //number picker
        numPicker = findViewById(R.id.number_picker);
        numPicker.setWrapSelectorWheel(true);
        // input field
        userInput = findViewById(R.id.userInput);
        // text fields
        avg_tV = findViewById(R.id.avg_textView);
        median_tV = findViewById(R.id.median_textView);
        //buttons
        findViewById(R.id.add_btn).setOnClickListener(add_Btn_Pressed);
        findViewById(R.id.view_btn).setOnClickListener(view_Btn_Pressed);
        // variables
        usersWords = new ArrayList<>();
        //text fields
        avg_tV.setText(getString(R.string.avg_wrd_length, String.valueOf(0)));
        median_tV.setText(getString(R.string.median_wrd_length, String.valueOf(0)));
        decFormat = new DecimalFormat(".##");
    }

    private final View.OnClickListener add_Btn_Pressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String userInputString = userInput.getText().toString().toLowerCase();
            if (!usersWords.contains(userInputString)){
                // validates textEdit isnt empty
                if (userInputString.equals("")){
                    presentToast(getString(R.string.is_empty));
                    // validates input string isnt composed of all spaces
                }else if(userInputString.trim().equals("")){
                    presentToast(getString(R.string.is_empty));
                    hideKeyBoard(userInput);
                    userInput.setText("");
                }else{
                    // runs if texst field isnt empty, has only spaces and its a unique word
                    usersWords.add(userInputString);
                    setAvgMedianText();
                    calcMedian();
                    userInput.setText("");
                    setNumPickerMinMax();
                    hideKeyBoard(userInput);
                }
            }else{
                presentToast(getString(R.string.already_exist));
                userInput.setText("");
            }
        }
    };
    // view btn pressed
    private final View.OnClickListener view_Btn_Pressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(usersWords.size() != 0){
                String selectedWord = usersWords.get(numPicker.getValue() - 1);
                showDialogs(selectedWord);
            }else{
                presentToast(getString(R.string.no_words_yet));
            }

        }
    };

    // calculates average of all words in users words list
    private double calcAverage(){
        double total = 0.0;
        for (String value : usersWords) {
            char[] charArray = value.toCharArray();
            total += charArray.length;
        }

        return  total / usersWords.size();
    }

    private double calcMedian(){
        Double median;
        // stores the count of each word in the usersWords list
        ArrayList<Double> wordCount = new ArrayList<>();
        // loops through each value, then gets the count of the word by converting it
        // to a char array and taking the lenght.
        for (String value : usersWords) {
            char[] charArray = value.toCharArray();
            wordCount.add((double) charArray.length);
        }
        Collections.sort(wordCount);
        //modulous to determine if the count is even or odd
        if (wordCount.size() % 2 == 0){
            median = (wordCount.get(wordCount.size()/2) + wordCount.get(wordCount.size()/2 - 1))/2;
        }else{
            median = wordCount.get(wordCount.size()/2);
        }
        return median;
    }

    private void presentToast(String message){
        Toast mToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    private void setNumPickerMinMax(){
        if (usersWords.isEmpty()){
            numPicker.setMinValue(0);
            numPicker.setMaxValue(0);
        }else{
            numPicker.setMinValue(1);
            numPicker.setMaxValue(usersWords.size());
        }
    }

    private void hideKeyBoard(EditText textFieldToDismiss){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textFieldToDismiss.getWindowToken(), 0);
    }
    // sets text for average and median
    private void setAvgMedianText(){
        Double avg = calcAverage();
        Double med = calcMedian();
        if (avg == Math.floor(avg)){
            // removes decimals from whole numbers
            avg_tV.setText(getString(R.string.avg_wrd_length, String.valueOf(avg.intValue())));
        }else{
            // includes decimals to two places
            avg_tV.setText(getString(R.string.avg_wrd_length, String.valueOf(decFormat.format(avg))));
        }
        if (med == Math.floor(med)){
            // removes decimals from whole numbers
            median_tV.setText(getString(R.string.median_wrd_length, String.valueOf(med.intValue())));
        }else{
            // includes decimals to two places
            median_tV.setText(getString(R.string.median_wrd_length, String.valueOf(decFormat.format(med))));
        }
    }
    // dialog for viewing individual words / deleting
    private void showDialogs(final String selectedWord){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_title);
        builder.setCancelable(false);
        builder.setMessage(selectedWord);
        builder.setNegativeButton(R.string.dialog_neg_btn, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                usersWords.remove(selectedWord);
                if (!usersWords.isEmpty()){
                    setNumPickerMinMax();
                    setAvgMedianText();
                }else{
                    avg_tV.setText(getString(R.string.median_wrd_length, String.valueOf(0)));
                    median_tV.setText(getString(R.string.median_wrd_length, String.valueOf(0)));
                    setNumPickerMinMax();
                }
            }
        });
        builder.setPositiveButton(R.string.dialog_pos_btn, null);
        builder.show();
    }
}
