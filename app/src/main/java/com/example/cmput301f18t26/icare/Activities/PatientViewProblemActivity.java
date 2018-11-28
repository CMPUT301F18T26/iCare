package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.IntentActions;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.example.cmput301f18t26.icare.R;

import java.util.Calendar;
import java.util.List;

public class PatientViewProblemActivity extends AppCompatActivity {

    private DataController dataController;
    private Problem selectedProblem;
    private TextView titleText;
    private TextView descriptionText;
    private TextView dateText;
    private List<BaseRecord> userRecordList;
    private ListView recordListView;
    private ArrayAdapter<BaseRecord> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_condition_view);
        //Setting the TextViews to variables for later use
        titleText = (TextView) findViewById(R.id.condition_view_name);
        descriptionText = (TextView) findViewById(R.id.condition_view_description);
        dateText = (TextView) findViewById(R.id.condition_view_date);

        //This gets the necessary data to display the Problem ListView
        dataController = DataController.getInstance();
        //currentUser = dataController.getCurrentUser();
        //problemList = dataController.getProblems(currentUser.getUID());
        recordListView = (ListView) findViewById(R.id.record_list_view);

        //Takes you to Add/Edit Problem screen telling it to EDIT
        Button editButton = (Button) findViewById(R.id.edit_condition);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent i = new Intent(v.getContext(), AddEditProblemActivity.class);
                i.putExtra("action", IntentActions.EDIT);
                startActivity(i);
            }
        });

        //Allows you to Add/Edit Record screen telling it to ADD
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add_new_record_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent i = new Intent(v.getContext(), AddEditRecordActivity.class);
                /**
                 * PLEASE FIX THIS TO USE THE NEW PASSING INTENT PATTERN, PASS A MESSAGE OF WHAT WE'RE
                 * DOING NOT A ID. USE THE SELECTEDRECORD FIELD IN DATACONTROLLER INSTEAD.
                 */
                i.putExtra("problemUID", IntentActions.ADD);
                startActivity(i);
            }
        });

        // Takes you to view record on press
        recordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (DataController.getInstance().checkInternet()) {
                    Object object = recordListView.getItemAtPosition(position);
                    BaseRecord record = BaseRecord.class.cast(object);
                    dataController.setSelectedRecord(record);

                    Intent i = new Intent(view.getContext(), ViewRecordActivity.class);
                    //i.putExtra("action", IntentActions.EDIT);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "You can only perform this action online.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    protected void onStart(){
        super.onStart();
        //Finding the necessary data to populate the text boxes and the Record List
        dataController = DataController.getInstance();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Finding the necessary data to populate the text boxes and the Record List
        selectedProblem = dataController.getSelectedProblem();
        //Log.i("Error", selectedProblem.getUID());

        userRecordList = dataController.getRecords(selectedProblem);
        recordListView = (ListView) findViewById(R.id.record_list_view);

        //Set the values of the text boxes
        setValues(selectedProblem);

        userRecordList = dataController.getRecords(selectedProblem);
        adapter = new ArrayAdapter<>(this, R.layout.userrecords_list_item,R.id.record_name,userRecordList);
        adapter.notifyDataSetChanged();
        recordListView.setAdapter(adapter);
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
}
