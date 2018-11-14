package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                //Once data for problems is created I can use these function calls to pass data
                /*
                Intent i = new Intent(view.getContext(), ViewProblemActivity.class);
                i.putExtra("Problem", problem);
                i.putExtra("Index", position);
                startActivity(i);
                */
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


}
