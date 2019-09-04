//Hayden Vass
// AID -1904
// Integrative 3
package com.example.aidday3class;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private View firstView;
    private View secondView;
    private Toast mToast;
    private EditText registerUserName;
    private EditText registerPasswordOne;
    private EditText registerPasswordTwo;
    private String userName;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstView = findViewById(R.id.firstView);
        secondView = findViewById(R.id.secondView);
        // text views on register page
        registerUserName = findViewById(R.id.registerTextUserName);
        registerPasswordOne = findViewById(R.id.registerPassword1);
        registerPasswordTwo = findViewById(R.id.registerPassword2);
        secondView.setVisibility(View.GONE);
        findViewById(R.id.registerButton).setOnClickListener(registerBtnClk);
        findViewById(R.id.cancelButton).setOnClickListener(cancel);
        findViewById(R.id.registerButton2).setOnClickListener(register);
        findViewById(R.id.loginButton).setOnClickListener(logIn);
    }
    // registration view
    View.OnClickListener registerBtnClk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            firstView.setVisibility(View.GONE);
            secondView.setVisibility(View.VISIBLE);
        }
    };
    // cancel / go back button
    View.OnClickListener cancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            firstView.setVisibility(View.VISIBLE);
            secondView.setVisibility(View.GONE);
        }
    };
    // register view register button click
    View.OnClickListener register = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String userInput = registerUserName.getText().toString();
            String passwordOne = registerPasswordOne.getText().toString();
            String passwordTwo = registerPasswordTwo.getText().toString();
            if (userInput.toCharArray().length < 5){
                // checks user name length
                registerUserName.setError("Username needs to be 5 characters or more.");
                registerUserName.requestFocus();
            }else if(passwordOne.toCharArray().length < 5){
                //checks password length
                registerPasswordOne.setError("Password needs to be 5 characters or more.");
                registerPasswordOne.requestFocus();
            }else if(!passwordOne.equals(passwordTwo)){
                //checks if passwords match
                registerPasswordTwo.setError("Passwords don't match.");
            }else{
                presentToast(getString(R.string.registereComplete));
                password = passwordTwo;
                userName = userInput;
                Button backButton = findViewById(R.id.cancelButton);
                backButton.setText(getString(R.string.back_text));
            }
        }
    };

    View.OnClickListener logIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText userInputUserName = findViewById(R.id.userNameLogInScreen1);
            EditText userPassword = findViewById(R.id.userPasswordScreenOne);
            if (userName != null && password != null){
                //if user made an account then the conditional checks to make sure the user name nad password match what they put.
                // if it doesnt then they get shown a toast that something went wrong.
                if(userName.contentEquals(userInputUserName.getText()) && password.contentEquals(userPassword.getText())){
                    presentToast("Log in successfull");
                }else{
                    presentToast("Username or password were incorrect.");
                }
            }else{
                presentToast("Please create an account");
            }

        }
    };

    private void presentToast(String message){
        Toast mToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
