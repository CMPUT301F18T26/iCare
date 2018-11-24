package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.R;

public class ViewPatientsActivity extends AppCompatActivity {

    // For passing the position of the selected patient to the next activity
    protected static final String SELECTED_PATIENT = "com.example.cmput301f18t26.icare.Activities.SELECTED_PATIENT";

    private FloatingActionButton addPatientButton;

    // Necessary patient list items
    private ListView patientList;
    private ArrayAdapter<Patient> patientListAdapter;

    // Singular data controller
    private DataController dataController;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_patients);

        dataController = DataController.getInstance();

        // initialize UI elements
        addPatientButton = findViewById(R.id.add_new_patient);

        // initialize patient list view items
        patientListAdapter = new ArrayAdapter<>(
                this,
                R.layout.activity_add_patient_seach_and_list_item,
                R.id.patient_name,
                dataController.getPatients()
        );

        patientList = findViewById(R.id.care_provider_patient_list);
        patientList.setAdapter(patientListAdapter);

        // Add action for clicking on a patient and viewing their problems
        patientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                Intent intent = new Intent(adapterView.getContext(), ViewPatientProblemsActivity.class);
                intent.putExtra(SELECTED_PATIENT, position);
                startActivity(intent);
            }
        });

        // Add action for clicking on a add patient
        addPatientButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(addPatientButton.getContext(), SearchAddPatientsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        // initialize patient list view items
        patientListAdapter = new ArrayAdapter<>(
                this,
                R.layout.activity_add_patient_seach_and_list_item,
                R.id.patient_name,
                dataController.getPatients()
        );
        // Setting adapter
        patientList.setAdapter(patientListAdapter);
        // Sending message of change in data
        patientListAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Creating the menu options from the xml file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
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
            case R.id.log_out:
                // Log the person out
                dataController.logout();
                dataController.writeDataToFiles(getApplicationContext());
                finish();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //https://stackoverflow.com/questions/4779954/disable-back-button-in-android
    /**
     * This function was overridden to do nothing on a back button press
     */
    @Override
    public void onBackPressed() {
        // Do nothing on back press except show an error message
        Toast.makeText(getApplicationContext(),
                "Error: Cannot go back from this activity.",
                Toast.LENGTH_SHORT).show();
    }

    // This activity is being stopped, saving data to file
    @Override
    public void onStop() {
        super.onStop();
        // Writing to file
        dataController.writeDataToFiles(getApplicationContext());
    }
}
