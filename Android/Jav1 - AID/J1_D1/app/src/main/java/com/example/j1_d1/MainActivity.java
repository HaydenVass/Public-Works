package com.example.j1_d1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity  {

    private TextView mTextView = null;

    private static final String TAG = "JAVA1DAY1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.tv_output);

        findViewById(R.id.btn_read).setOnClickListener(mBtnClickRead);
        findViewById(R.id.butn_write).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String example = getString(R.string.textonthis);
                Log.i(TAG, "WRITE BUTTON: " + example);
                mTextView.setText(example);
            }
        });

    }

    private View.OnClickListener mBtnClickRead = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String textViewText = (String)mTextView.getText();
            Log.i(TAG, "READ BUTTON: " + textViewText);
        }
    };

}
