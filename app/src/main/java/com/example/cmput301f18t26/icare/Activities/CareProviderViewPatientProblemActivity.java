package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.IntentActions;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

import java.util.Calendar;
import java.util.List;

public class CareProviderViewPatientProblemActivity extends AppCompatActivity {
    private Problem problem;
    private User user;
    private ListView recordListView;
    private ArrayAdapter adapter;
    private List<BaseRecord> recordList;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Creating the UI view
        setContentView(R.layout.activity_care_provider_condition_view);
        // First we need the user information
        Intent intent = getIntent();
        String userID = intent.getExtras().getString("user_id");
        user = User.fetchPatientInformation(userID);
        // Getting the problem information
        problem = DataController.getInstance().getSelectedProblem();
        // Getting the record list view
        recordListView = findViewById(R.id.record_list_view);

        // Now setting up the listener which will allow care provider to add comments
        FloatingActionButton addCommentButton = findViewById(R.id.add_new_comment_to_problem);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent i = new Intent(v.getContext(), CareProviderAddCommentActivity.class);
                // Adding information to show
                i.putExtra("user_id", user.getUID());
                i.putExtra("problem_id", problem.getUID());
                DataController.getInstance().setSelectedProblem(problem);
                startActivity(i);
            }
        });

        // Takes you to view record on press
        recordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = recordListView.getItemAtPosition(position);
                BaseRecord record = BaseRecord.class.cast(object);
                DataController.getInstance().setSelectedRecord(record);

                Intent i = new Intent(view.getContext(), ViewRecordActivity.class);
                //i.putExtra("action", IntentActions.EDIT);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Here we first get all the records associated with the problem
        recordList = DataController.getInstance().getRecords(problem);
        // Now we get an adapter
        adapter = new ArrayAdapter<>(this, R.layout.userrecords_list_item, R.id.record_name, recordList);
        adapter.notifyDataSetChanged();
        recordListView.setAdapter(adapter);
        // Now We display the information at the top
        TextView problemName = findViewById(R.id.condition_view_name);
        problemName.setText(problem.getTitle());
        // Patient name
        TextView patientName = findViewById(R.id.condition_view_patient_name);
        patientName.setText(user.getUsername());
        // Date of the problem creation
        Calendar date = problem.getDate();
        TextView dateView = findViewById(R.id.condition_view_date);
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;      // 0 to 11
        int day = date.get(Calendar.DAY_OF_MONTH);

        String strdate = day + "/" + month + "/" + year;
        dateView.setText(strdate);
        // Now we show the description
        TextView desc = findViewById(R.id.condition_view_description);
        desc.setText(problem.getDescription());
    }
}
