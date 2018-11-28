package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.R;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewSearchResultsActivity extends AppCompatActivity {

    private DataController dataController;

    List<BaseRecord> recordList;
    List<Problem> problemList;

    ArrayAdapter<BaseRecord> recordAdapter;
    ArrayAdapter<Problem> problemAdapter;

    ListView results;

    ToggleButton toggleResults = findViewById(R.id.toggle_results);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_results);

        dataController = DataController.getInstance();

        recordList = dataController.getRecordSearchResults();
        problemList = dataController.getProblemSearchResults();

        recordAdapter = new ArrayAdapter<>(
                this,
                R.layout.userrecords_list_item,
                recordList
        );
        problemAdapter = new ArrayAdapter<>(
                this,
                R.layout.problems_list_item,
                problemList
        );

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
    }
}
