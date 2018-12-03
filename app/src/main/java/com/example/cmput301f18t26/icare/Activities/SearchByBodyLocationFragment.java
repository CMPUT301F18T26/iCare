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
import com.example.cmput301f18t26.icare.R;

/**
 * Code pattern from:
 * https://stackoverflow.com/questions/25905086/multiple-buttons-onclicklistener-android
 */
public class SearchByBodyLocationFragment extends Fragment implements View.OnClickListener {

    DataController dataController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bodylocation, container, false);
        dataController = DataController.getInstance();

        rootView.findViewById(R.id.front_head_button).setOnClickListener(this);
        rootView.findViewById(R.id.front_neck_shoulders_button).setOnClickListener(this);
        rootView.findViewById(R.id.front_right_arm_button).setOnClickListener(this);
        rootView.findViewById(R.id.front_left_arm_button).setOnClickListener(this);
        rootView.findViewById(R.id.front_chest_button).setOnClickListener(this);
        rootView.findViewById(R.id.front_stomach_button).setOnClickListener(this);
        rootView.findViewById(R.id.front_right_leg_button).setOnClickListener(this);
        rootView.findViewById(R.id.front_left_leg_button).setOnClickListener(this);
        rootView.findViewById(R.id.back_head_button).setOnClickListener(this);
        rootView.findViewById(R.id.back_neck_shoulders_button).setOnClickListener(this);
        rootView.findViewById(R.id.back_right_arm_button).setOnClickListener(this);
        rootView.findViewById(R.id.back_left_arm_button).setOnClickListener(this);
        rootView.findViewById(R.id.back_upper_back_button).setOnClickListener(this);
        rootView.findViewById(R.id.back_lower_back_button).setOnClickListener(this);
        rootView.findViewById(R.id.back_right_leg_button).setOnClickListener(this);
        rootView.findViewById(R.id.back_left_leg_button).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onPause(){
        super.onPause();
        Bundle savedInstanceState = new Bundle();
        onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.front_head_button:
                dataController.searchByBodyLocation(BodyLocation.FRONT_HEAD);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.front_neck_shoulders_button:
                dataController.searchByBodyLocation(BodyLocation.FRONT_NECK_SHOULDERS);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.front_right_arm_button:
                dataController.searchByBodyLocation(BodyLocation.FRONT_RIGHT_ARM);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.front_left_arm_button:
                dataController.searchByBodyLocation(BodyLocation.FRONT_LEFT_ARM);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.front_chest_button:
                dataController.searchByBodyLocation(BodyLocation.FRONT_CHEST);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.front_stomach_button:
                dataController.searchByBodyLocation(BodyLocation.FRONT_STOMACH);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.front_right_leg_button:
                dataController.searchByBodyLocation(BodyLocation.FRONT_RIGHT_LEG);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.front_left_leg_button:
                dataController.searchByBodyLocation(BodyLocation.FRONT_LEFT_LEG);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.back_head_button:
                dataController.searchByBodyLocation(BodyLocation.BACK_HEAD);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.back_neck_shoulders_button:
                dataController.searchByBodyLocation(BodyLocation.BACK_NECK_SHOULDERS);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.back_right_arm_button:
                dataController.searchByBodyLocation(BodyLocation.BACK_RIGHT_ARM);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.back_left_arm_button:
                dataController.searchByBodyLocation(BodyLocation.BACK_LEFT_ARM);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.back_upper_back_button:
                dataController.searchByBodyLocation(BodyLocation.BACK_UPPER_BACK);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.back_lower_back_button:
                dataController.searchByBodyLocation(BodyLocation.BACK_LOWER_BACK);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.back_right_leg_button:
                dataController.searchByBodyLocation(BodyLocation.BACK_RIGHT_LEG);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
            case R.id.back_left_leg_button:
                dataController.searchByBodyLocation(BodyLocation.BACK_LEFT_LEG);
                intent = new Intent(v.getContext(), ViewSearchResultsActivity.class);
                break;
        }
        startActivity(intent);
    }
}
