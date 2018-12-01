package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.R;

import java.util.List;

public class ViewSearchResultsActivity extends AppCompatActivity {

    private DataController dataController;

    List<BaseRecord> recordList;
    List<Problem> problemList;

    ArrayAdapter<BaseRecord> recordAdapter;
    ArrayAdapter<Problem> problemAdapter;

    ListView results;

    Switch toggleResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_results);

        dataController = DataController.getInstance();

        results = findViewById(R.id.search_results);
        toggleResults = findViewById(R.id.toggle_results);

        toggleResults.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    results.setAdapter(recordAdapter);
                } else {
                    // The toggle is disabled
                    results.setAdapter(problemAdapter);
                }
            }
        });

        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (results.getAdapter() == recordAdapter) {
                    dataController.setSelectedRecord(recordList.get(position));
                    intent = new Intent(getApplicationContext(), ViewRecordActivity.class);
                } else {
                    dataController.setSelectedProblem(problemList.get(position));
                    intent = new Intent(getApplicationContext(), PatientViewProblemActivity.class);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        recordList = dataController.getRecordSearchResults();
        problemList = dataController.getProblemSearchResults();
        for (Problem p : problemList){
            p.setNumRecords(dataController.getRecords(p).size());
        }

        recordAdapter = new ArrayAdapter<>(
                this,
                R.layout.userrecords_list_item,
                R.id.record_name,
                recordList
        );
        problemAdapter = new ArrayAdapter<>(
                this,
                R.layout.problems_list_item,
                R.id.condition_name,
                problemList
        );

        results.setAdapter(problemAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();

        recordList = dataController.getRecordSearchResults();
        problemList = dataController.getProblemSearchResults();
        results.setAdapter(problemAdapter);
    }
}
