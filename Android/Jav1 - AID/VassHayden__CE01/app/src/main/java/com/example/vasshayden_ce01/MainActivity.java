//Hayden Vass
//JAV 1 - 1904
//VassHayden_CE01

package com.example.vasshayden_ce01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    private TextView clickCounterTextView = null;
    private Integer numbClicks = 0;
    private String textForClickCounterText;
    private Character [] characterArray;

    private Button [] buttonArray;
    private  Button button1;
    private  Button button2;
    private  Button button3;
    private  Button button4;
    private  Button button5;
    private  Button button6;
    private  Button button7;
    private  Button button8;
    private  Button button9;
    private  Button button10;
    private  Button button11;
    private  Button button12;
    private  Button button13;
    private  Button button14;
    private  Button button15;
    private  Button button16;
    private  Button resetButton;
    private Integer userCounter;

    private String userFirstChoice;
    private String userSecondChoice;
    private Integer winCounter;
    private String previousSelectedID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userCounter = 1;
        characterArray = new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        //shuffles array
        Collections.shuffle(Arrays.asList(characterArray));
        winCounter = 0;
        previousSelectedID = " ";

        //sets text for click counter
        clickCounterTextView = findViewById(R.id.tv_output);
        textForClickCounterText = getString(R.string.number_of_clicks, String.valueOf(numbClicks));
        clickCounterTextView.setText(textForClickCounterText);

        //reset button object
        resetButton = findViewById(R.id.reset_Btn);
        resetButton.setText(R.string.reset);
        resetButton.setVisibility(View.INVISIBLE);
        resetButton.setOnClickListener(resetBtnClicked);

        //creates button object for each button in the view
        button1 = findViewById(R.id.btn_1);
        button1.setOnClickListener(oneBtnClicked);

        button2 = findViewById(R.id.btn_2);
        button2.setOnClickListener(twoBtnClicked);

        button3 = findViewById(R.id.btn_3);
        button3.setOnClickListener(threeBtnClicked);

        button4 = findViewById(R.id.btn_4);
        button4.setOnClickListener(fourBtnClicked);

        button5 = findViewById(R.id.btn_5);
        button5.setOnClickListener(fiveBtnClicked);

        button6 = findViewById(R.id.btn_6);
        button6.setOnClickListener(sixBtnClicked);

        button7 = findViewById(R.id.btn_7);
        button7.setOnClickListener(sevenBtnClicked);

        button8 = findViewById(R.id.btn_8);
        button8.setOnClickListener(eightBtnClicked);

        button9 = findViewById(R.id.btn_9);
        button9.setOnClickListener(nineBtnClicked);

        button10 = findViewById(R.id.btn_10);
        button10.setOnClickListener(tenBtnClicked);

        button11 = findViewById(R.id.btn_11);
        button11.setOnClickListener(elevenBtnClicked);

        button12 = findViewById(R.id.btn_12);
        button12.setOnClickListener(twelveBtnClicked);

        button13 = findViewById(R.id.btn_13);
        button13.setOnClickListener(thirteenBtnClicked);

        button14 = findViewById(R.id.btn_14);
        button14.setOnClickListener(fourteenBtnClicked);

        button15 = findViewById(R.id.btn_15);
        button15.setOnClickListener(fifteenBtnClicked);

        button16 = findViewById(R.id.btn_16);
        button16.setOnClickListener(sixteenBtnClicked);

        buttonArray = new Button[]{button1, button2, button3, button4, button5, button6,
        button7, button8, button9, button10, button11, button12, button13, button14,
        button15, button16};

        // sets tags for each button in buttonArray
        for (int i = 0; i < buttonArray.length; i++) {
            buttonArray[i].setTag(characterArray[i]);
        }
    }

    private final View.OnClickListener oneBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button1);
        }
    };

    private final View.OnClickListener twoBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button2);
        }
    };

    private final View.OnClickListener threeBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //Log.i(TAG, String.valueOf(button3.getTag()));
            checkUserChocies(v, button3);
        }
    };
    private final View.OnClickListener fourBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button4);
        }
    };

    private final View.OnClickListener fiveBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button5);
        }
    };

    private final View.OnClickListener sixBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button6);
        }
    };
    private final View.OnClickListener sevenBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button7);
        }
    };
    private final View.OnClickListener eightBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v,button8);
        }
    };

    private final View.OnClickListener nineBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button9);
        }
    };

    private final View.OnClickListener tenBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button10);
        }
    };
    private final View.OnClickListener elevenBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button11);
        }
    };

    private final View.OnClickListener twelveBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button12);
        }
    };

    private final View.OnClickListener thirteenBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button13);
        }
    };
    private final View.OnClickListener fourteenBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button14);
        }
    };

    private final View.OnClickListener fifteenBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button15);
        }
    };

    private final View.OnClickListener sixteenBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkUserChocies(v, button16);
        }
    };


    private void checkUserChocies(View v, Button b){
        //updateCounterText();
        //conditional to check the tags of the buttons clicked held in users choices
        if(userCounter == 1) {
            //checks to see if the user is on their first click, if so they set the value of the views tag to
            //users first choice
            previousSelectedID = String.valueOf(v.getId());
            userFirstChoice = String.valueOf(v.getTag());
            b.setText(String.valueOf(v.getTag()));
            previousSelectedID = String.valueOf(v.getId());
            userCounter++;
            updateCounterText();
        }else if (userCounter == 2){
            if (!previousSelectedID.equals(String.valueOf(v.getId()))){
                //checks to see if the user is on their second click, if so they set the value of the views tag to
                //users second choice
                userSecondChoice = String.valueOf(v.getTag());
                b.setText(String.valueOf(v.getTag()));
                previousSelectedID = String.valueOf(v.getId());

                userCounter++;
                if(winCounter == 7){
                    resetButton.setVisibility(View.VISIBLE);
                    hideButtons();
                }
                updateCounterText();
            }
        }else if(userCounter == 3){
            //makes sure the user cant click the same button twice in a row
            if (!previousSelectedID.equals(String.valueOf(v.getId()))){
                //if the buttons are different, checks to see if the buttons match
                if (userFirstChoice.equals(userSecondChoice)){
                    // if the users first and second choice are the same
                    hideButtons();
                    winCounter++;
                }
                // resets the users choice counter to 1
                userCounter = 2;
                //sets all button text to an empty string
                for (Button button : buttonArray) {
                    button.setText(" ");
                }
                userFirstChoice = String.valueOf(v.getTag());
                b.setText(String.valueOf(v.getTag()));
                updateCounterText();
                //sets the previous ID to the current choice so the user cant pick
                //the same button in the next round
                previousSelectedID = String.valueOf(v.getId());
            }
        }

    }

    private final View.OnClickListener resetBtnClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //resets counters
            userCounter = 1;
            numbClicks = 0;
            winCounter = 0;
            //reshuffles character array to reassign the tag for the buttons
            Collections.shuffle(Arrays.asList(characterArray));
            for (int i = 0; i < buttonArray.length; i++) {
                // sets visibility, new tags and text of buttons after reset
                buttonArray[i].setTag(characterArray[i]);
                buttonArray[i].setVisibility(View.VISIBLE);
                buttonArray[i].setText(" ");
            }
            //sets the number of clicks back to 0
            textForClickCounterText = getString(R.string.number_of_clicks, String.valueOf(numbClicks));
            clickCounterTextView.setText(textForClickCounterText);
            resetButton.setVisibility(View.INVISIBLE);
        }
    };

    //hides correct matches
    private void hideButtons(){
        for (Button button : buttonArray) {
            if (button.getTag().toString().equals(userFirstChoice) || button.getTag().toString().equals(userSecondChoice)) {
                button.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void updateCounterText(){
        //incriments click counter
        numbClicks ++;
        // set text for counter
        textForClickCounterText = getString(R.string.number_of_clicks, String.valueOf(numbClicks));
        clickCounterTextView.setText(textForClickCounterText);
    }

}
