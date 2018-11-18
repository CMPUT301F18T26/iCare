package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.ProblemFactory;
import com.example.cmput301f18t26.icare.Controllers.RecordFactory;
import com.example.cmput301f18t26.icare.Controllers.UserRecordFactory;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.Record;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.example.cmput301f18t26.icare.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditRecordActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private TextView dateStamp;
    private ImageView images;

    private String problemUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        problemUID = extras.getString("problemUID");

        setContentView(R.layout.activity_add_edit_record);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        Fragment InfoFragment = new Fragment();
        //InfoFragment.setArguments(bundle);

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
