package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.R;

public class ViewPatientProblemsActivity extends AppCompatActivity {

    DataController dataController;

    private TextView patientName;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_problem_view);

        dataController = DataController.getInstance();

        patientName = findViewById(R.id.condition_view_patient_name);
        patientName.setText(dataController.getCurrentUser().getUsername());

        /*
        Work in progress. Requires storing Problems in ES to pull them here
         */
    }
}
