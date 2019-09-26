//hayden vass
// jav1 -1904
//main activity
package com.example.vasshayden_ce08;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText userInput;
    private ArrayList<String> usersWords;
    private ListView mListView;
    private ArrayAdapter<String> arrayAdapter;
    private final String userTextKey = "userInputText";
    private final String arrayKey = "arrayKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            usersWords = savedInstanceState.getStringArrayList(arrayKey);
        }else{ usersWords = new ArrayList<>();}
        userInput = findViewById(R.id.user_input);
        findViewById(R.id.add_button).setOnClickListener(add_Btn_Pressed);
        mListView = findViewById(R.id.words_list_view);
        setUpArrayAdapterView();
    }

    private final View.OnClickListener add_Btn_Pressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String userInputString = userInput.getText().toString();

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
                    arrayAdapter.notifyDataSetChanged();
                    userInput.setText("");
                    hideKeyBoard(userInput);
                }
            }else{
                presentToast(getString(R.string.already_exist));
                userInput.setText("");
            }
        }
    };

    private void presentToast(String message){
        Toast mToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    private void hideKeyBoard(EditText textFieldToDismiss){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textFieldToDismiss.getWindowToken(), 0);
    }

    // constructd array adapter and set them to both list and grid view.
    private void setUpArrayAdapterView(){
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, usersWords);
        mListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(userTextKey, userInput.getText().toString());
        outState.putStringArrayList(arrayKey, usersWords);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userInput.setText(savedInstanceState.getString(userTextKey));
    }
}
