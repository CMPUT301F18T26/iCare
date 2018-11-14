package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_condition);

        titleEntry = (EditText) findViewById(R.id.condition_name);
        descriptionEntry = (EditText) findViewById(R.id.description);
        dateEntry = (DatePicker) findViewById(R.id.date_picker);

        dataController = DataController.getInstance();
        user = dataController.getCurrentUser();

        //Not sure if I need this
        Intent i = getIntent();
        setValues(i);


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
     * @param i
     */
    void setValues(Intent i){
        int index = i.getIntExtra("Index", -1);

        //If you are adding a new problem. Using Intents because that's how I did it for
        //FeelsBook, but may have to change now that we are using DataController.
        if (index == -1){
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

        //If you are editing an old problem
        else{
            final Problem problem = (Problem)i.getSerializableExtra("Problem");

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

        //Create a new problem in the ProblemFactory. Haven't gotten around to editing yet.
        Problem problem = ProblemFactory.getProblem(title, date, description, userUID);
        dataController.addProblem(problem);
        Toast.makeText(getApplicationContext(),
                "Problem added successfully",
                Toast.LENGTH_SHORT).show();
        //Returns to the ListView of the Problems.
        finish();
    }
}
