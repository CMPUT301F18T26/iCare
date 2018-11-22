package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.IntentActions;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

import java.util.ArrayList;
import java.util.List;

public class PatientViewProblemListActivity extends AppCompatActivity {

    private DataController dataController;
    private ListView oldProblemList;
    private List<Problem> problemList;
    private ArrayAdapter<Problem> adapter;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_conditions_list);

        //This gets the necessary data to display the Problem ListView
        dataController = DataController.getInstance();
        currentUser = dataController.getCurrentUser();
        problemList = dataController.getProblems(currentUser);
        oldProblemList = (ListView) findViewById(R.id.patient_conditions_list_view);

        // Takes you to view problem on press
        oldProblemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = oldProblemList.getItemAtPosition(position);
                Problem problem = Problem.class.cast(object);
                dataController.setSelectedProblem(problem);
                Intent i = new Intent(view.getContext(), PatientViewProblemActivity.class);
                i.putExtra("action", IntentActions.EDIT);
                startActivity(i);
            }
        });

        // Takes you to add a problem on press
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add_new_problem_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent i = new Intent(v.getContext(), AddEditProblemActivity.class);
                i.putExtra("action", IntentActions.ADD);
                startActivity(i);
            }
        });
    }


    protected void onStart() {
        super.onStart();
        problemList = dataController.getProblems(currentUser);
        adapter = new ArrayAdapter<>(this,
               R.layout.problems_list_item,R.id.condition_name,
                problemList);
        adapter.notifyDataSetChanged();
        oldProblemList.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        problemList = dataController.getProblems(currentUser);
        adapter = new ArrayAdapter<>(this,
                R.layout.problems_list_item,R.id.condition_name,
                problemList);
        adapter.notifyDataSetChanged();
        oldProblemList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Creating the menu options from the xml file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.patient_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Set the currentProblem as the one we've selected
        int id = item.getItemId();
        // Executing code depending on which item is selected
        switch (id){
            case R.id.contact_information:
                // Creating the intent
                intent = new Intent(PatientViewProblemListActivity.this, ViewProfileActivity.class);
                // Passing in the user id that will have its information displayed
                intent.putExtra("user_id", currentUser.getUID());
                // Launching the intent
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
