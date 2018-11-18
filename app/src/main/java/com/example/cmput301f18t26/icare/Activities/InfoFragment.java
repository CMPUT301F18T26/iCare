package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.RecordFactory;
import com.example.cmput301f18t26.icare.Models.Record;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class InfoFragment extends Fragment{
    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private TextView dateStamp;
    private String problemUID;
    private ImageView images;
    private User user;

    Calendar cal = Calendar.getInstance();
    Date date=cal.getTime();
    DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
    String formattedDate=dateFormat.format(date);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataController = DataController.getInstance();
        user = dataController.getCurrentUser();

        //passing the problem ID not sure if we will need this - tyler
        problemUID = dataController.getCurrentProblem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        //Get everything we need for the View
        titleEntry = (EditText) rootView.findViewById(R.id.record_title);
        descriptionEntry = (EditText) rootView.findViewById(R.id.record_comment);
        dateStamp =  rootView.findViewById(R.id.record_date_and_time);
        dateStamp.setText(formattedDate);

        //Saves your Record and returns you to the Record List View

        Button saveButton = (Button) rootView.findViewById(R.id.userRecord_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().setResult(RESULT_OK);
                save();
                //TODO: add check to make sure values entered correctly
            }

        });


        return rootView;
    }

    public void save(){
        //Get the values of the Title, Date and Description fields
       String title = titleEntry.getText().toString().trim();
       String description = descriptionEntry.getText().toString().trim();
       user = dataController.getCurrentUser();
       String userUID = user.getUID();

        //TODO: NEED TO GET THE CORRECT GETUSERRECORD CONSTRUCTOR TO BE CALLED DEPENDING ON WHAT IS SPECIFIED BY THE USER
        //TODO: FOR NOW IT DEFAULT USES GEO BODY AND PHOTOS NOT SPECIFIED UNTIL THOSE HAVE BEEN IMPLEMENTED


            //Create a new record in the userRecordFactory.

            Record userRecord = RecordFactory.getRecord(title, formattedDate, description, problemUID, null, null, null );
            Log.d("tyler,2","hello"+userRecord.toString());
            dataController.addUserRecord(userRecord);
            Toast.makeText(getActivity(), "User Record added successfully", Toast.LENGTH_SHORT).show();

        //Returns to the problem description and list of records for that problem.
        getActivity().finish();

        //Intent i = new Intent(getActivity(), PatientViewProblemActivity.class);
        //startActivity(i);
    }
}
