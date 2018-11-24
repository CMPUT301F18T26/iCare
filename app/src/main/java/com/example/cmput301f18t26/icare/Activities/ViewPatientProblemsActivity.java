package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.R;

import java.util.List;

public class ViewPatientProblemsActivity extends AppCompatActivity {

    private DataController dataController;
    private TextView patientName;
    private Patient patient;
    private ListView patientProblemListView;
    private List<Problem> problemList;
    private ArrayAdapter<Problem> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Creating whats needed for the activity to work
        setContentView(R.layout.activity_care_provider_problem_view);

        Integer position = getIntent().getIntExtra(ViewPatientsActivity.SELECTED_PATIENT, 0);

        dataController = DataController.getInstance();
        // get the patient from the current care providers fetched patient list
        patient = dataController.getPatients().get(position);
        // Getting the list view
        patientProblemListView = findViewById(R.id.care_provider_patient_list_view);

        Button viewProfileButton = findViewById(R.id.view_profile);

        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // View contact information
                // Creating the intent
                Intent intent = new Intent(ViewPatientProblemsActivity.this, ViewProfileActivity.class);
                // Passing in the user id that will have its information displayed
                intent.putExtra("user_id", patient.getUID());
                // Launching the intent
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        // This will be done every time a resume happens
        // grab the patient name text view and reset the patient name to the current patients name
        patientName = findViewById(R.id.condition_view_patient_name);
        patientName.setText(patient.getUsername());
        // Get the list of problems a patient has
        problemList = dataController.getProblems(patient.getUID());
        // Getting an adapter for the array
        adapter = new ArrayAdapter<>(this,
                R.layout.problems_list_item,R.id.condition_name,
                problemList);
        // Connecting adapter and view
        patientProblemListView.setAdapter(adapter);
        // Data changed
        adapter.notifyDataSetChanged();
    }
}
