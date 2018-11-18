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
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_conditions_list);

        /**
         * This gets the necessary data to display the Problem ListView.
         * I have not yet tried to see if returning to the ListView for the same
         * user will populate the ListView correctly, because how the app is currently
         * setup I need to create a new user each time.
         */
        dataController = DataController.getInstance();
        user = dataController.getCurrentUser();
        problemList = dataController.getProblems(user);
        oldProblemList = (ListView) findViewById(R.id.patient_conditions_list_view);

        //Takes you to view problem when problem list item is clicked
        oldProblemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Listens for click on list view
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object object = oldProblemList.getItemAtPosition(position);
                Problem problem = Problem.class.cast(object);
                String problemUID = problem.getUID();
                Intent i = new Intent(view.getContext(), PatientViewProblemActivity.class);
                i.putExtra("problemUID", problemUID);
                startActivity(i);
            }
        });

        //Takes you to add a problem on press
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add_new_problem_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent i = new Intent(v.getContext(), AddEditProblemActivity.class);
                startActivity(i);
            }
        });
    }


    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        problemList = dataController.getProblems(user);
        adapter = new ArrayAdapter<Problem>(this,
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
        // Getting the id of the menu item selected
        int id = item.getItemId();
        // Executing code depending on which item is selected
        switch (id){
            case R.id.contact_information:
                // Creating the intent
                intent = new Intent(PatientViewProblemListActivity.this, ViewProfileActivity.class);
                // Passing in the user id that will have its information displayed
                intent.putExtra("user_id", this.user.getUID());
                // Launching the intent
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
