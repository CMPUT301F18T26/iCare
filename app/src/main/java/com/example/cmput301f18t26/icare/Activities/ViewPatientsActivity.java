package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

public class ViewPatientsActivity extends AppCompatActivity {

    private ListView patientList;
    private FloatingActionButton addPatientButton;

    private ArrayAdapter<User> patientListAdapter;

    // Singular data controller
    private DataController dataController;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_patients);

        dataController = DataController.getInstance();

//        patientList = findViewById(R.id.care_provider_patient_list);
//        addPatientButton = findViewById(R.id.add_new_patient);

//        patientListAdapter = new ArrayAdapter<User>(
//                this, R.layout.activity_care_provider_patients_list_item, dataController.getPatients()
//        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Creating the menu options from the xml file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.care_provider_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Getting the id of the menu item selected
        int id = item.getItemId();
        // Executing code depending on which item is selected
        switch (id){
            case R.id.contact_information:
                // Creating the intent
                intent = new Intent(ViewPatientsActivity.this, ViewProfileActivity.class);
                // Passing in the user id that will have its information displayed
                intent.putExtra("user_id", this.dataController.getCurrentUser().getUID());
                // Launching the intent
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
