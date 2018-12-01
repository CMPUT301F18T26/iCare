package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        // Care provider can view a patient's profile/contact information
        Button viewProfileButton = findViewById(R.id.view_profile);

        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (DataController.getInstance().checkInternet()) {
                    // View contact information
                    // Creating the intent
                    Intent intent = new Intent(ViewPatientProblemsActivity.this, ViewProfileActivity.class);
                    // Passing in the user id that will have its information displayed
                    intent.putExtra("user_id", patient.getUID());
                    // Launching the intent
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "You can only perform this action online.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Care provider can view records associated with a patient's problem
        patientProblemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (DataController.getInstance().checkInternet()) {
                    // Getting the tapped object
                    Object object = patientProblemListView.getItemAtPosition(position);
                    Problem problem = Problem.class.cast(object);

                    // Creating the intent and putting the information it needs
                    Intent i = new Intent(adapterView.getContext(), CareProviderViewPatientProblemActivity.class);
                    i.putExtra("user_id", patient.getUID());
                    i.putExtra("problem_id", problem.getUID());
                    dataController.setSelectedProblem(problem);
                    // Starting the intent
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "You can only perform this action online.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Creating the menu options from the xml file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, SearchRecordsProblemsActivity.class);
        dataController.setSearchUserUID(patient.getUID());
        startActivity(intent);
        return true;
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
        for (Problem p : problemList){
            p.setNumRecords(dataController.getRecords(p).size());
        }
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
