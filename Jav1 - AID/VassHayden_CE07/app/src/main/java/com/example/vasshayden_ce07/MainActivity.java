//Hayden Vass
// JAV1 - 1904
// Main.Activity.java

package com.example.vasshayden_ce07;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnFinished{


    private GridView mGridView;
    private ArrayList<Books> mBookList;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar_view);
        mBookList = new ArrayList<>();
        if (isConnected()){
            //if an internet connection exist. Checks api for data
            findGridView();
            GetBooksAsync grabBooksTask = new GetBooksAsync(MainActivity.this);
            grabBooksTask.execute(getString(R.string.web_site));
        }else{
            //if no internet connection exist, presents toast informing the user.
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }

    }

    private void findGridView() {
        try {
            mGridView = findViewById(R.id.cell);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setUpBaseAdapter(){
        if(mGridView != null){
            BookAdapter ba = new BookAdapter(this, mBookList);
            mGridView.setAdapter(ba);
        }
    }

    private boolean isConnected(){
        ConnectivityManager mgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(mgr != null){
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if (info != null){
                return info.isConnected();
            }
        }
        return false;
    }
    @Override
    public void onFinished(ArrayList<Books> booksList) {
        // assigns mBooklist to the list of books pulled from API then
        // assigns the adapter to grid view.
        mBookList = booksList;
        progressBar.setVisibility(View.GONE);
        setUpBaseAdapter();
    }

    //starts progress bar when data tries to be pulled
    @Override
    public void onProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }
}
