package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.R;

public class SearchRecordsProblemsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private DataController dataController;

    // Three possible fragments for searching
    private Fragment keywordFragment;
    private Fragment geoFragment;
    private Fragment bodyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataController = DataController.getInstance();

        setContentView(R.layout.activity_patient_search_records_problems);
        BottomNavigationView navigation = findViewById(R.id.search_navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        keywordFragment = new SearchByKeywordFragment();
        bodyFragment = new SearchByBodyLocationFragment();

        loadFragment(keywordFragment);
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
            case R.id.search_by_keyword:
                fragment = keywordFragment;
                break;

            case R.id.search_by_location:
                fragment = geoFragment;
                break;

            case R.id.search_by_body:
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
