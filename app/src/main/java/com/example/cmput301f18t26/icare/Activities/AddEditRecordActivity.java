package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.R;

public class AddEditRecordActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_record);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new InfoFragment());//display Info Fragment By default - Tyler
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


    //called whenever one of the bottom nav buttons is selected - tyler
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        //track which object is clicked - tyler
        switch(menuItem.getItemId()){
            case R.id.info:
                fragment = new InfoFragment();
                break;

            case R.id.geo:
                fragment = new GeolocationFragment();
                break;

            case R.id.body:
                fragment = new BodylocationFragment();
                break;
        }

        return loadFragment(fragment);
    }



}
