package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PatientViewProblemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_condition_view);
        Intent i = getIntent();
        setValues(i);

        /**
         * I think this should delete the Problem, not the record.
         */
        //Deletes problem and returns you to the Problem List View
        Button deleteButton = (Button) findViewById(R.id.delete_record_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                //delete();
            }
        });

        /**
         * I think this should be edit Problem, not edit Record
         */
        //Takes you to Add/Edit Problem screen
        Button editButton = (Button) findViewById(R.id.edit_record);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                //start intent (AddEditProblemActivity);
            }
        });
    }

    void setValues(Intent i) {
        final Problem problem = (Problem) i.getSerializableExtra("Problem");

        //Title
        TextView title = (TextView) findViewById(R.id.condition_view_name);
        title.setText(problem.getTitle());

        //Description
        TextView description = (TextView) findViewById(R.id.condition_view_description);
        description.setText(problem.getDescription());

        //Date
        //Used below for sdf instructions
        //https://coderanch.com/t/412082/java/Convert-Calendar-String
        Calendar c = problem.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strdate = sdf.format(c);
        TextView date = (TextView) findViewById(R.id.condition_view_date);
        date.setText(strdate);
    }
}
