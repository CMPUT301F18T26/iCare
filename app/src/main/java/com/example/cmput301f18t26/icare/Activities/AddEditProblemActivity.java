package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.R;

import java.util.Calendar;

public class AddEditProblemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_condition);
        Intent i = getIntent();
        setValues(i);

        //Saves your Problem and returns you to the Problem List View
        Button saveButton = (Button) findViewById(R.id.save_problem);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Boolean check = checkValuesEntered();
                if (check == false){
                    Toast.makeText(AddEditProblemActivity.this,
                            "Title and Description Must Be Entered",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    //save();
                }
            }
        });
    }

    /**
     * Sets the values of the fields in the Add/Edit Problem Activity.
     * @param i
     */
    void setValues(Intent i){
        int index = i.getIntExtra("Index", -1);

        //If you are adding a new problem
        if (index == -1){
            //Title
            EditText title = (EditText) findViewById(R.id.condition_name);
            title.setHint("Enter Title");

            //Description
            EditText description = (EditText) findViewById(R.id.description);
            description.setHint("Enter description");

            //Date
            //Used below to set date picker
            //https://stackoverflow.com/questions/6451837/how-do-i-set-the-current-date-in-a-datepicker
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePicker date = (DatePicker) findViewById(R.id.date_picker);
            date.init(year, month, day, null);

        }

        //If you are editing an old problem
        else{
            final Problem problem = (Problem)i.getSerializableExtra("Problem");

            //Title
            EditText title = (EditText) findViewById(R.id.condition_name);
            title.setText(problem.getTitle());

            //Description
            EditText description = (EditText) findViewById(R.id.description);
            description.setText(problem.getDescription());

            //Date
            Calendar c = problem.getDate();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePicker date = (DatePicker) findViewById(R.id.date_picker);
            date.init(year, month, day, null);
        }
    }

    /**
     * Checks to see if user entered values for all fields
     * @return
     */
    boolean checkValuesEntered(){
        //Title
        EditText title = (EditText) findViewById(R.id.condition_name);
        String titleCheck = title.getText().toString();
        //Description
        EditText description = (EditText) findViewById(R.id.description);
        String desCheck = description.getText().toString();

        return !desCheck.isEmpty() && !titleCheck.isEmpty();
    }
}
