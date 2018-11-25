package com.example.cmput301f18t26.icare.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class ViewInfoFragment extends Fragment {

        private DataController dataController;
        private TextView titleText;
        private TextView descriptionText;
        private TextView dateStamp;
        private Problem selectedProblem;
        private ImageView images;
        private User user;
        private BaseRecord selectedRecord;

        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
        String formattedDate=dateFormat.format(date);


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            dataController = DataController.getInstance();
            user = dataController.getCurrentUser();

            //passing the problem ID not sure if we will need this - tyler
            selectedProblem = dataController.getSelectedProblem();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_info, container, false);

            //Get everything we need for the View
            titleText = (TextView) rootView.findViewById(R.id.view_record_title);
            descriptionText = (TextView) rootView.findViewById(R.id.record_comment);
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

            this.selectedRecord = dataController.getSelectedRecord();
            setValues(this.selectedRecord);

            return rootView;
        }

        public void save(){
            //Get the values of the Title, Date and Description fields
            String title = titleText.getText().toString().trim();
            String description = descriptionText.getText().toString().trim();
            user = dataController.getCurrentUser();
            String userUID = user.getUID();
            // Since this is a user created record
            int recType = 0;

            //Create a new record in the userRecordFactory.
            BaseRecord record = RecordFactory.getRecord(formattedDate, description, selectedProblem.getUID(), null, null, null, recType, title);
            dataController.addRecord(record);
            Toast.makeText(getActivity(), "User Record added successfully", Toast.LENGTH_SHORT).show();

            //Returns to the problem description and list of records for that problem.
            getActivity().finish();
        }

    void setValues(BaseRecord record) {
        //Title
        titleText.setText(record.getTitle());
        //Description
        descriptionText.setText(record.getComment());

        //Date
        String strdate = record.getDate();
        dateStamp.setText(strdate);
    }

}
