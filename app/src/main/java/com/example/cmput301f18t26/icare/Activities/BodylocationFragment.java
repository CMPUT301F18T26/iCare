package com.example.cmput301f18t26.icare.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.BodyLocation;
import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.example.cmput301f18t26.icare.R;

import static android.app.Activity.RESULT_OK;

//implements View.OnClickListener
public class BodylocationFragment extends Fragment  {

    private DataController dataController;
    private User user;
    private Problem selectedProblem;
    private String name;
    BodyLocation bodyLocation;
    private TextView title;
    private UserRecord record;

    //View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataController = DataController.getInstance();
        user = dataController.getCurrentUser();
        selectedProblem = dataController.getSelectedProblem();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View newView = inflater.inflate(R.layout.fragment_bodylocation, null);


        title = (TextView) newView.findViewById(R.id.patient_record_body_label_location);
        Button front_head_button = (Button) newView.findViewById(R.id.front_head_button);
        front_head_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().setResult(RESULT_OK);
                name = "Front Head";
                title.setText(name);
                //record.setBodyLocation(name);
            }
        });

        //front_head_button.setOnClickListener(this);
        return newView;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState){
//
//    }

    /*
    @Override
    public void onClick(View view) {
        title = (TextView) getView().findViewById(R.id.patient_record_body_label_location);

        switch (view.getId()) {
            //If the front side is clicked
            case R.id.front_head_button:
                Log.d("fuck", "front head");
                name = "Front Head";
                title.setText(name);
                bodyLocation.setBodyLocation(name);
                break;
        }
    }
*/
/*
    //public void onClickMethod
    //When a button on the stick figure is clicked, selects the body location
    public void chooseBodyLocation(View view) {

        title = (TextView) getView().findViewById(R.id.patient_record_body_label_location);
        switch (view.getId()) {
            //If the front side is clicked
            case R.id.front_head_button:
                Log.d("fuck", "front head");
                name = "Front Head";
                title.setText(name);
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.front_neck_shoulders_button:
                name = "Front Neck Shoulders";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.front_torso_button:
                name = "Torso";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.front_stomach_button:
                name = "Stomach";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.front_right_arm_button:
                name = "Front Right Arm";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.front_left_arm_button:
                name = "Front Left Arm";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.front_right_leg_button:
                name = "Front Right Leg";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.front_left_leg_button:
                name = "Front Left Leg";
                bodyLocation.setBodyLocation(name);
                break;

            //If the back side is clicked
            case R.id.back_head_button:
                name = "Back Head";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.back_neck_shoulders_button:
                name = "Back Neck Shoulders";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.back_upper_back_button:
                name = "Upper Back";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.back_lower_back_button:
                name = "Lower Back";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.back_right_arm_button:
                name = "Back Right Arm";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.back_left_arm_button:
                name = "Back Left Arm";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.back_right_leg_button:
                name = "Back Right Leg";
                bodyLocation.setBodyLocation(name);
                break;
            case R.id.back_left_leg_button:
                name = "Back Left Leg";
                bodyLocation.setBodyLocation(name);
                break;
        }
    }
*/

}
