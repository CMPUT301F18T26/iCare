package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

        patientList = findViewById(R.id.care_provider_patient_list);
        addPatientButton = findViewById(R.id.add_new_patient);

//        patientListAdapter = new ArrayAdapter<User>(
//                this, R.layout.activity_care_provider_patients_list_item, dataController.getPatients()
//        );
    }
}
