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
        // Creating whats needed for the activity to work
        setContentView(R.layout.activity_care_provider_problem_view);

        Integer position = getIntent().getIntExtra(ViewPatientsActivity.SELECTED_PATIENT, 0);

        dataController = DataController.getInstance();
        // get the patient from the current care providers fetched patient list
        patient = dataController.getPatients().get(position);
    }

    @Override
    protected void onResume(){
        super.onResume();
        // This will be done every time a resume happens
        // grab the patient name text view and reset the patient name to the current patients name
        patientName = findViewById(R.id.condition_view_patient_name);
        patientName.setText(patient.getUsername());
    }
}
