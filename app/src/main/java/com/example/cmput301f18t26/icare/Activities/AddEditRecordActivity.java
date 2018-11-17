package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
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
    //private Calendar cal;
    private String problemUID;
    private ImageView images;

    Calendar cal = Calendar.getInstance();
    //Date date=cal.getTime();
    //DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
    //String strdate = dateFormat.format(cal);
    String strdate = "pussy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_record);
        loadFragment(new InfoFragment());//display Info Fragment By default - Tyler

        Log.d("tyler","get here1");
        titleEntry = (EditText) findViewById(R.id.record_title);
        descriptionEntry = (EditText) findViewById(R.id.record_comment);
        dateStamp =  findViewById(R.id.record_date_and_time);

        dateStamp.setText(strdate);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        Log.d("tyler","get here2");

        Log.d("tyler","get here3");
        //Saves your Record and returns you to the Record List View
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                save();
                //TODO: add check to make sure values entered correctly
            }

        });
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

    public void save(){
        //Get the values of the Title, Date and Description fields
        String title = titleEntry.getText().toString().trim();
        String description = descriptionEntry.getText().toString().trim();

        /**
         * This checks to see if the userRecord exists. If the record does not exist, create
         * a new one.
         * If it does exist, call updateUserRecord from dataController, which will take the old
         * record and replace it with the new record.
         */

        //TODO: NEED TO GET THE CORRECT GETUSERRECORD CONSTRUCTOR TO BE CALLED DEPENDING ON WHAT IS SPECIFIED BY THE USER
        //FOR NOW IT DEFAULT USES GEO BODY AND PHOTOS NOT SPECIFIED UNTIL THOSE HAVE BEEN IMPLEMENTED

        if (dataController.getUserRecord(problemUID) == null){
            //Create a new record in the userRecordFactory. Haven't gotten around to editing yet.

            //String title, String date, String description, Location location, BodyLocation bodyLocation, ArrayList<String> photos, String problemUID
            UserRecord userRecord = UserRecordFactory.getUserRecord(title, strdate, description, problemUID);
            dataController.addUserRecord(userRecord);
            Toast.makeText(getApplicationContext(),
                    "User Record added successfully",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Record oldRecord = dataController.getRecord(problemUID);
            Record newRecord = RecordFactory.getRecord(title, strdate, description, problemUID);

            //TODO: implement update problem
            //dataController.updateProblem(oldProblem, newProblem);
            Toast.makeText(getApplicationContext(),
                    "Problem edited successfully",
                    Toast.LENGTH_SHORT).show();
        }

        Log.d("tyler","I believe we go here");
        //Returns to the ListView of the Problems.
        Intent i = new Intent(this, PatientViewProblemActivity.class);
        startActivity(i);
    }

}
