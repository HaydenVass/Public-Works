//Hayden Vass
//
package com.example.vasshaydenaid_ce03;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //radio buttons - view
    RadioGroup viewRadioGroup;
    RadioButton checkedViewRadioBtn;
    //radio buttons - text color
    RadioGroup textRadioGroup;
    RadioButton checkedTextColorBtn;
    //color view
    ImageView colorView;
    //edit text field
    EditText userInput;
    //frame layout text view
    TextView reflectedText;
    //switch
    Switch styleSwitch;
    
    static final String TAG = "TODAY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //radio buttons - view color
        viewRadioGroup = findViewById(R.id.view_radio_grp);
        checkedViewRadioBtn = viewRadioGroup.findViewById(viewRadioGroup.getCheckedRadioButtonId());
        //radio buttons - text color
        textRadioGroup = findViewById(R.id.radioGrou_txtColor);
        checkedTextColorBtn = textRadioGroup.findViewById(textRadioGroup.getCheckedRadioButtonId());
        //color image view
        colorView = findViewById(R.id.color_view);
        //Edit text
        userInput = findViewById(R.id.display_text_et);

        //frame layout text field
        reflectedText = findViewById(R.id.FL_user_text);

        //switch
        styleSwitch = findViewById(R.id.style_switch);
        // sets event handlers
        toggleBold();
        setViewColor();
        setTextColor();
        textChanged();
    }

    private void setViewColor(){
        viewRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId){
                    case R.id.purple_radiobg_btn:
                        colorView.setColorFilter(getColor(R.color.purple));
                        break;
                    case R.id.green_radiobg_btn:
                        colorView.setColorFilter(getColor(R.color.green));
                        break;
                    case R.id.black_radiobg_button:
                        colorView.setColorFilter(getColor(R.color.black));
                        break;
                }
            }
        });
    }


    private void setTextColor(){
        textRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId){
                    case R.id.purple_radiotxt_btn:
                        reflectedText.setTextColor(getColor(R.color.purple));
                        break;
                    case R.id.green_radiotxt_btn:
                        reflectedText.setTextColor(getColor(R.color.green));
                        break;
                    case R.id.black_radiotxt_button:
                        reflectedText.setTextColor(getColor(R.color.black));
                        break;
                }
            }
        });
    }

    private void toggleBold(){
        styleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (styleSwitch.isChecked()){
                    reflectedText.setTypeface(null, Typeface.BOLD);
                }else{
                    reflectedText.setTypeface(null, Typeface.NORMAL);
                }
            }
        });
    }

    private void textChanged(){
        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0) {
                    reflectedText.setText(userInput.getText());
                }else{
                    reflectedText.setText(getString(R.string.text_appears_here));
                }
            }
        });



    }
}
