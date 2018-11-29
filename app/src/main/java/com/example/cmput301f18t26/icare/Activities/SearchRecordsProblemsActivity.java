package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.R;

public class SearchRecordsProblemsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private DataController dataController;

    // Three possible fragments for searching
    private Fragment keywordFragment = new KeywordFragment();
    private Fragment geoFragment = new GeolocationFragment();
    private Fragment bodyFragment = new BodylocationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        dataController = DataController.getInstance();

        setContentView(R.layout.activity_patient_search_records_problems);
        BottomNavigationView navigation = findViewById(R.id.search_navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        keywordFragment = new KeywordFragment();
        loadFragment(keywordFragment);//display keyword Fragment By default - Tyler
    }


    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }


    //called whenever one of the bottom nav buttons is selected
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        //track which object is clicked
        switch(menuItem.getItemId()){
            case R.id.info:
                fragment = keywordFragment;
                break;

            case R.id.geo:
                fragment = geoFragment;
                break;

            case R.id.body:
                fragment = bodyFragment;
                break;

        }

        return loadFragment(fragment);
    }

    // This activity is being stopped, saving data to file
    @Override
    public void onStop() {
        super.onStop();
        // Writing to file
        dataController.writeDataToFiles(getApplicationContext());
    }


}
