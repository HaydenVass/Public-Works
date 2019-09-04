//Hayden Vass
//JAV2 - 1905
//Main Activity

package com.example.vasshaydewn_ce01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.vasshaydewn_ce01.Objects.Article;
import com.example.vasshaydewn_ce01.fragments.MainListFragment;

import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetArticlesAsync.OnFinished {

    private ArrayList<Article> horseArticles;
    private ArrayList<Article> motoArticles;
    private ArrayList<Article> mountainArticles;
    private static final String FILE_HORSELIST = "horse.txt";
    private static final String FILE_MOTOLIST = "moto.txt";
    private static final String FILE_MOUNTAINLIST = "mountain.txt";
    private TextView emptyState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyState = findViewById(R.id.empty_stateTV);
        // set up spinner
        setUpSpinner();
        //inital fragment for first spinner item.
        replaceFragment(horseArticles);
    }

    private void setUpSpinner(){
        // construction for UI spinner
        Spinner sOne = findViewById(R.id.article_selector);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource
                (this, R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sOne.setAdapter(adapter1);
        sOne.setOnItemSelectedListener(oneListener);
    }


    //click listener for spinner
    private final AdapterView.OnItemSelectedListener oneListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selection = parent.getItemAtPosition(position).toString();
            switch (selection){
                // toggles views - replace fragments
                case "Horses":
                    //checks network connectivity - if connected, API call is made
                    // URL / context/ sender ID sent as perameteres to filter what list gets updated.
                    if(NetworkUtils.isConnected(MainActivity.this)){
                        getAPIData("https://www.reddit.com/r/horses/hot.json", 0);
                    }else{
                        horseArticles = LoadSerializable(FILE_HORSELIST);
                        replaceFragment(horseArticles);
                        //checks to see count of selected list and if the count is 0, the toast
                        //is presented to the user telling them there is no saved data.
                        presentToast(horseArticles);
                    }

                    break;
                case "Motorcycles":
                    // same functionality of statement above
                    if(NetworkUtils.isConnected(MainActivity.this)){
                        getAPIData("https://www.reddit.com/r/motorcycles/hot.json", 1);
                    }else{
                        motoArticles = LoadSerializable(FILE_MOTOLIST);
                        replaceFragment(motoArticles);
                        presentToast( motoArticles);
                    }
                    break;
                case "Mountains":
                    // same functionality of statement above
                    if(NetworkUtils.isConnected(MainActivity.this)){
                        getAPIData("https://www.reddit.com/r/mountains/hot.json", 2);
                    }else{
                        mountainArticles = LoadSerializable(FILE_MOUNTAINLIST);
                        replaceFragment(mountainArticles);
                        presentToast(mountainArticles);
                    }
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    };

    @Override
    public void SendArticlesBack(ArrayList<Article> pulledArticles, int sender) {
        emptyState.setVisibility(View.GONE);
        switch (sender){
            // toggles views - replace fragments
            // switches off ID of the sending list category
            case 0:
                horseArticles = pulledArticles;
                replaceFragment(horseArticles);
                SaveSerializable(FILE_HORSELIST, horseArticles);
                break;
            case 1:
                motoArticles = pulledArticles;
                replaceFragment(motoArticles);
                SaveSerializable(FILE_MOTOLIST, motoArticles);
                break;
            case 2:
                mountainArticles = pulledArticles;
                replaceFragment(mountainArticles);
                SaveSerializable(FILE_MOUNTAINLIST, mountainArticles);
                break;
        }
    }

    // saves list to inernal memory
    private void SaveSerializable(String key, ArrayList<Article> _selectedArticle){
        try{
            FileOutputStream fos = openFileOutput(key, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_selectedArticle);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //loads list from internal memory
    private ArrayList<Article> LoadSerializable(String key){
        ArrayList<Article> articlesToSend = new ArrayList<>();
        try{
            FileInputStream fis = openFileInput(key);
            ObjectInputStream ois = new ObjectInputStream(fis);
            articlesToSend = (ArrayList<Article>)ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return articlesToSend;

    }
    // toast with custom message pending size of the ArrayLIst
    private void presentToast( ArrayList<Article> selectedList){
        if(selectedList.size() == 0){
            emptyState.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, getString(R.string.no_stored_data), Toast.LENGTH_SHORT).show();
        }else{
            emptyState.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, getString(R.string.stored_data), Toast.LENGTH_SHORT).show();
        }
    }
    //swaps fragments pending passed in list
    private void replaceFragment(ArrayList<Article> _articles){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                MainListFragment.newInstance(_articles)).commit();
    }
    //takes in url and assigns arbitrary id to assign data to the proper list
    private void getAPIData(String _selectedAPI, int _sender){
        GetArticlesAsync mountainTask = new GetArticlesAsync
                (_selectedAPI,
                        MainActivity.this, _sender);
        mountainTask.execute();
    }

}
