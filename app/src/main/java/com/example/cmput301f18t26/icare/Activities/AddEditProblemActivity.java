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
import com.example.cmput301f18t26.icare.IntentActions;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

import java.util.Calendar;

public class AddEditProblemActivity extends AppCompatActivity {

    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private DatePicker dateEntry;

    private Problem selectedProblem;
    private IntentActions action;


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

        // Grab our enum for the action from intent
        Intent i = getIntent();
        action = (IntentActions) i.getSerializableExtra("action");

        setValues();

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
    private void setValues(){
        // If you are adding a new problem the intent action enum passed should be ADD
        if (action == IntentActions.ADD) {
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
        // If you are editing an old problem the intent action enum passed should be EDIT
        else if (action == IntentActions.EDIT) {
            selectedProblem = dataController.getSelectedProblem();
            //Title
            titleEntry.setText(selectedProblem.getTitle());
            //Description
            descriptionEntry.setText(selectedProblem.getDescription());
            //Date
            Calendar c = selectedProblem.getDate();
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
        // If you are adding a new problem the intent action enum passed should be ADD
        if (action == IntentActions.ADD) {
            String userUID = dataController.getCurrentUser().getUID();
            Problem problem = ProblemFactory.getProblem(title, date, description, userUID);
            dataController.addProblem(problem);
            Toast.makeText(getApplicationContext(),
                    "Problem added successfully",
                    Toast.LENGTH_SHORT).show();
        }
        // If you are editing an old problem the intent action enum passed should be EDIT
        else if (action == IntentActions.EDIT) {
            selectedProblem.setTitle(title);
            selectedProblem.setDescription(description);
            selectedProblem.setDate(date);
            dataController.updateProblem(selectedProblem);
            Toast.makeText(getApplicationContext(),
                    "Problem edited successfully",
                    Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
