package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.R;

public class ViewPatientProblemsActivity extends AppCompatActivity {

    DataController dataController;

    private TextView patientName;

    private Patient patient;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_problem_view);

        Integer position = getIntent().getIntExtra(ViewPatientsActivity.SELECTED_PATIENT, 0);

        dataController = DataController.getInstance();
        // get the patient from the current care providers fetched patient list
        patient = dataController.getPatients().get(position);

        // grab the patient name text view and reset the patient name to the current patients name
        patientName = findViewById(R.id.condition_view_patient_name);
        patientName.setText(patient.getUsername());

        /*
        Work in progress. Requires storing Problems in ES to pull them here
         */
    }
}
