package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.Record;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.example.cmput301f18t26.icare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PatientViewProblemActivity extends AppCompatActivity {

    private DataController dataController;
    private Problem problem;
    private String problemUID;
    private TextView titleText;
    private TextView descriptionText;
    private TextView dateText;
    private List<Record> userRecordList;
    private ListView oldRecordList;
    private ArrayAdapter<Record> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_condition_view);

        //Setting the TextView's
        titleText = (TextView) findViewById(R.id.condition_view_name);
        descriptionText = (TextView) findViewById(R.id.condition_view_description);
        dateText = (TextView) findViewById(R.id.condition_view_date);

        //Retrieving problemUID from previous page and then finding the correct Problem.
        Intent i = getIntent();
        problemUID = (String) i.getSerializableExtra("problemUID");
        dataController = DataController.getInstance();
        problem = dataController.getProblem(problemUID);

        userRecordList = dataController.getUserRecords(problem);
        oldRecordList = (ListView) findViewById(R.id.record_list_view);


        //Set the values of the page
        setValues(problem);

        //Deletes problem and returns you to the Problem List View
        Button deleteButton = (Button) findViewById(R.id.delete_record_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                delete(problem);
            }
        });


        //Takes you to Add/Edit Problem screen
        Button editButton = (Button) findViewById(R.id.edit_condition);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent i = new Intent(v.getContext(), AddEditProblemActivity.class);
                i.putExtra("problemUID", problemUID);
                startActivity(i);
            }
        });

        //Allows you to add a new record to the list for that problem
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add_new_record_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("tyler","this is before the addedit activity");
                setResult(RESULT_OK);
                Log.d("tyler","this is before the addedit activity");
                Intent i = new Intent(v.getContext(), AddEditRecordActivity.class);
                i.putExtra("problemUID", problemUID);
                startActivity(i);
            }
        });
    }

    protected void onStart(){
        super.onStart();
        Log.d("tyler,2","does this run when we exit other activity?");

        userRecordList = dataController.getUserRecords(problem); // not sure if this is correct - Tyler
        adapter = new ArrayAdapter<Record>(this, R.layout.userrecords_list_item,R.id.record_name,userRecordList);
        adapter.notifyDataSetChanged();
        oldRecordList.setAdapter(adapter);
    }

    void setValues(Problem problem) {
        //Title
        titleText.setText(problem.getTitle());
        //Description
        descriptionText.setText(problem.getDescription());

        //Date
        Calendar c = problem.getDate();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;      // 0 to 11
        int day = c.get(Calendar.DAY_OF_MONTH);
        String strdate = day + "/" + month + "/" + year;
        dateText.setText(strdate);
    }

    void delete(Problem problem){
        dataController.deleteProblem(problem);
        Intent i = new Intent(this, PatientViewProblemListActivity.class);
        startActivity(i);
    }
}
