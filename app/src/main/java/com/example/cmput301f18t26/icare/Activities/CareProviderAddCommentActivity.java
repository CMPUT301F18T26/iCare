package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.RecordFactory;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CareProviderAddCommentActivity extends AppCompatActivity {
    private Problem problem;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // First we need the user information
        Intent intent = getIntent();
        String userID = intent.getExtras().getString("user_id");
        user = User.fetchPatientInformation(userID);
        // Getting the problem information
        problem = DataController.getInstance().getSelectedProblem();
        // Creating whats needed for the activity to work, showing UI
        setContentView(R.layout.activity_care_provider_add_comment);
        // Settings up all the details
        // Patient name
        TextView patientNameView = findViewById(R.id.patient_name);
        patientNameView.setText(user.getUsername());
        // Problem name
        TextView problemNameView = findViewById(R.id.problem_name);
        problemNameView.setText(problem.getTitle());
        // Problem date
        // Date of the problem creation
        Calendar date = problem.getDate();
        TextView dateView = findViewById(R.id.problem_date);
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;      // 0 to 11
        int day = date.get(Calendar.DAY_OF_MONTH);
        String strdate = day + "/" + month + "/" + year;
        dateView.setText(strdate);

        // Time stamp for this comment
        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
        final String formattedTime = dateFormat.format(time);
        TextView timeStampView = findViewById(R.id.record_comment_time);

        // Now setting up the on click listener on the save button
        Button saveButton = findViewById(R.id.save_new_comment);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // https://stackoverflow.com/questions/6290531/check-if-edittext-is-empty
                // Checking if comment is empty
                if (DataController.getInstance().checkInternet()) {
                    EditText commentView = (EditText) findViewById(R.id.comment);
                    String comment = commentView.getText().toString();
                    if (comment.matches("")) {
                        Toast.makeText(CareProviderAddCommentActivity.this, "Error: Invalid comment.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        //Get the values of the Title, Date and Description fields
                        String title = "Doctor's Comment";
                        String description = commentView.getText().toString().trim();
                        String userUID = user.getUID();
                        // Since this is a user created record
                        int recType = 1;

                        //Create a new record in the userRecordFactory.
                        BaseRecord record = RecordFactory.getRecord(formattedTime, description, problem.getUID(), null, null, null, recType, title);
                        DataController.getInstance().addRecord(record);
                        Toast.makeText(CareProviderAddCommentActivity.this, "Doctor Comment added successfully", Toast.LENGTH_SHORT).show();

                        //Returns to the problem description and list of records for that problem.
                        CareProviderAddCommentActivity.this.finish();
                    }
                } else {
                    Toast.makeText(CareProviderAddCommentActivity.this, "You can only perform this action online.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
