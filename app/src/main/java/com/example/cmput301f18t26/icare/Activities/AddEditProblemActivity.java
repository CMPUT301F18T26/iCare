package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.ProblemFactory;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

import java.util.Calendar;

public class AddEditProblemActivity extends AppCompatActivity {

    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private DatePicker dateEntry;
    private User user;
    private String problemUID;
    private Problem problem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_condition);
        //Setting the TextViews to variables for later use
        titleEntry = (EditText) findViewById(R.id.condition_name);
        descriptionEntry = (EditText) findViewById(R.id.description);
        dateEntry = (DatePicker) findViewById(R.id.date_picker);
        //Getting the instance of the Data controller to retrieve data from
        dataController = DataController.getInstance();
        user = dataController.getCurrentUser();

        Intent i = getIntent();
        problemUID = (String) i.getSerializableExtra("problemUID");
        setValues(problemUID);

        //Saves your Problem and returns you to the Problem List View
        Button saveButton = (Button) findViewById(R.id.save_problem);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                save();
                //TODO: add check to make sure values entered correctly
            }

        });
    }

    /**
     * Sets the values of the fields in the Add/Edit Problem Activity.
     * @param
     */
    void setValues(String problemUID){
        //If you are adding a new problem, the problemUID will be null so it sets the text boxes
        //as hints.
        if (problemUID == null){
            //Title
            titleEntry.setHint("Enter Title");
            //Description
            descriptionEntry.setHint("Enter description");
            //Date
            //Used below to set date picker
            //https://stackoverflow.com/questions/6451837/how-do-i-set-the-current-date-in-a-datepicker
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            dateEntry.init(year, month, day, null);
        }

        //If you are editing an old problem, it retrieves the correct information and sets the
        //text boxes accordingly.
        else{
            problem = dataController.getProblem(problemUID);
            //Title
            titleEntry.setText(problem.getTitle());
            //Description
            descriptionEntry.setText(problem.getDescription());
            //Date
            Calendar c = problem.getDate();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            dateEntry.init(year, month, day, null);
        }
    }

    /**
     * Saves the entered values to DataController. Doesn't check if values are entered correctly or
     * at all.
     */
    public void save(){
        //Get the values of the Title, Date and Description fields
        String title = titleEntry.getText().toString().trim();
        String description = descriptionEntry.getText().toString().trim();
        Calendar date = Calendar.getInstance();
        date.set(
                dateEntry.getYear(),
                dateEntry.getMonth(),
                dateEntry.getDayOfMonth()
        );
        user = dataController.getCurrentUser();
        String userUID = user.getUID();

        /**
         * This checks to see if the problem exists. If the problem does not exist, create
         * a new one.
         * If it does exist, call updateProblem from dataController, which will take the old
         * Problem and replace it with the new problem.
         */
        if (dataController.getProblem(problemUID) == null){
            //Create a new problem in the ProblemFactory. Haven't gotten around to editing yet.
            Problem problem = ProblemFactory.getProblem(title, date, description, userUID);
            dataController.addProblem(problem);
            Toast.makeText(getApplicationContext(),
                    "Problem added successfully",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Problem oldProblem = dataController.getProblem(problemUID);
            Problem newProblem = ProblemFactory.getProblem(title, date, description, userUID);
            String newProblemUID = newProblem.getUID();
            dataController.saveUID(newProblemUID);
            dataController.updateProblem(oldProblem, newProblem);
            Toast.makeText(getApplicationContext(),
                    "Problem edited successfully",
                    Toast.LENGTH_SHORT).show();
        }

        //Returns to the previous screen
        finish();
    }
}
