package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAddPatientsActivity extends AppCompatActivity{

    private TextView patientSearch;
    private Button searchButton;

    // Patient List objects
    private ListView patientList;
    private List<Patient> patients;
    private ArrayAdapter<Patient> patientListAdapter;

    private DataController dataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_search_and_list);

        patientSearch = findViewById(R.id.patient_search);
        searchButton = findViewById(R.id.search_patients_button);

        dataController = DataController.getInstance();

        patients = new ArrayList<>();
        patientList = findViewById(R.id.care_provider_patient_list_view);
        patientListAdapter = new ArrayAdapter<>(
                this,
                R.layout.activity_care_provider_patients_list_item,
                R.id.patient_name,
                patients
        );
        patientList.setAdapter(patientListAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*
                 Do not pass a UID -- this will get all patients that are currently
                 not assigned a doctor
                 */
                patients.addAll(dataController.searchPatients(patientSearch.getText().toString()));
                patientListAdapter.notifyDataSetChanged();
            }
        });

        patientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                /*
                Modify the patient to contain the careProviderUID instead of an
                empty careProviderUID
                 */
                Patient patient = patients.get(position);
                patient.setCareProviderUID(dataController.getCurrentUser().getUID());
                dataController.updateElasticSearchForNewUserInfo(patient);

                Toast.makeText(getApplicationContext(),
                        "Patient Added!",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}
