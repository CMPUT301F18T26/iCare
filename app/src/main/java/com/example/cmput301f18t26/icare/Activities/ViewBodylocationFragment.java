package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.BodyLocation;
import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.example.cmput301f18t26.icare.R;

public class ViewBodylocationFragment extends Fragment {

    private DataController dataController;
    private TextView title;
    private BaseRecord currentRecord;
    private BodyLocation bodyLocation = null;
    private String bodyLocationString;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bodyView = inflater.inflate(R.layout.fragment_view_bodylocation, null);
        dataController = DataController.getInstance();

        //Title of the page
        title = (TextView) bodyView.findViewById(R.id.patient_record_body_label_location);
        //Find the record we are working with
        currentRecord = dataController.getSelectedRecord();

        // Now getting the bodyLocation if the class is a UserRecord
        if (UserRecord.class == currentRecord.getClass()) {
            // Getting the record as a user record
            UserRecord userRecord = (UserRecord) currentRecord;
            //Find the bodyLocation
            bodyLocation = userRecord.getBodyLocation();
        }

        //Sets the title at the top to the bodyLocation String.
        if (bodyLocation != null){
            bodyLocationString = bodyLocation.getBodyLocation();
            title.setText(bodyLocationString);
        }
        return bodyView;
    }
}
