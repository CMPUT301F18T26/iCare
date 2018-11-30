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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.BodyLocation;
import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.R;

public class BodyLocationFragment extends Fragment {

    private DataController dataController;
    private TextView title;
    private String bodyLocation;
    private ImageView frontBody;
    private ImageView backBody;
    int[] IMAGES ={R.drawable.head_selected,
            R.drawable.shoulders,
            R.drawable.upper_torso,
            R.drawable.lower_torso,
            R.drawable.right_arm,
            R.drawable.left_arm,
            R.drawable.right_leg,
            R.drawable.left_leg,
            R.drawable.man_fixed_page};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View bodyView = inflater.inflate(R.layout.fragment_bodylocation, null);
        dataController = DataController.getInstance();
        frontBody = (ImageView) bodyView.findViewById(R.id.front_body_location_photo);
        backBody = (ImageView) bodyView.findViewById(R.id.back_body_location_photo);

        //Title of the page
        title = (TextView) bodyView.findViewById(R.id.patient_record_body_label_location);

        /**
         * The below button listeners listen for a click on the stick figure. Upon a click, the
         * image and header text will represent your selection and your selection will
         * be saved in DataController.
         */
        //Front head
        Button front_head_button = (Button) bodyView.findViewById(R.id.front_head_button);
        front_head_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Removes previous selection from image if there was one
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                //Sets body location string to match enum options
                bodyLocation = "FRONT_HEAD";
                //Changes the header text
                title.setText("Front Head");
                //Saves the enum selection in the dataController to pass to InfoFragment
                dataController.setCurrentBodyLocation(bodyLocation);
                //Sets the stick figure to your new selection
                frontBody.setImageResource(IMAGES[0]);
            }
        });

        //Front Neck and Shoulders
        Button front_neck_shoulders_button = (Button) bodyView.findViewById(R.id.front_neck_shoulders_button);
        front_neck_shoulders_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "FRONT_NECK_SHOULDERS";
                title.setText("Front Neck and Shoulders");
                dataController.setCurrentBodyLocation(bodyLocation);
                frontBody.setImageResource(IMAGES[1]);
            }
        });

        //Front Chest
        Button front_chest_button = (Button) bodyView.findViewById(R.id.front_chest_button);
        front_chest_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "FRONT_CHEST";
                title.setText("Chest");
                dataController.setCurrentBodyLocation(bodyLocation);
                frontBody.setImageResource(IMAGES[2]);
            }
        });

        //Front Stomach
        Button front_stomach_button = (Button) bodyView.findViewById(R.id.front_stomach_button);
        front_stomach_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "FRONT_STOMACH";
                title.setText("Stomach");
                dataController.setCurrentBodyLocation(bodyLocation);
                frontBody.setImageResource(IMAGES[3]);
            }
        });

        //Front Right Arm
        Button front_right_arm_button = (Button) bodyView.findViewById(R.id.front_right_arm_button);
        front_right_arm_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "FRONT_RIGHT_ARM";
                title.setText("Front Right Arm");
                dataController.setCurrentBodyLocation(bodyLocation);
                frontBody.setImageResource(IMAGES[4]);

            }
        });

        //Front Left Arm
        Button front_left_arm_button = (Button) bodyView.findViewById(R.id.front_left_arm_button);
        front_left_arm_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "FRONT_LEFT_ARM";
                title.setText("Front Left Arm");
                dataController.setCurrentBodyLocation(bodyLocation);
                frontBody.setImageResource(IMAGES[5]);

            }
        });

        //Front Right Leg
        Button front_right_leg_button = (Button) bodyView.findViewById(R.id.front_right_leg_button);
        front_right_leg_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "FRONT_RIGHT_LEG";
                title.setText("Front Right Leg");
                dataController.setCurrentBodyLocation(bodyLocation);
                frontBody.setImageResource(IMAGES[6]);
            }
        });

        //Front Left Leg
        Button front_left_leg_button = (Button) bodyView.findViewById(R.id.front_left_leg_button);
        front_left_leg_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "FRONT_LEFT_LEG";
                title.setText("Front Left Leg");
                dataController.setCurrentBodyLocation(bodyLocation);
                frontBody.setImageResource(IMAGES[7]);
            }
        });

        //Back Head
        Button back_head_button = (Button) bodyView.findViewById(R.id.back_head_button);
        back_head_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "BACK_HEAD";
                title.setText("Back Head");
                dataController.setCurrentBodyLocation(bodyLocation);
                backBody.setImageResource(IMAGES[0]);
            }
        });

        //Back Neck and Shoulders
        Button back_neck_shoulders_button = (Button) bodyView.findViewById(R.id.back_neck_shoulders_button);
        back_neck_shoulders_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "BACK__NECK_SHOULDERS";
                title.setText("Back Neck and Shoulders");
                dataController.setCurrentBodyLocation(bodyLocation);
                backBody.setImageResource(IMAGES[1]);
            }
        });

        //Back Upper Back
        Button back_upper_back_button = (Button) bodyView.findViewById(R.id.back_upper_back_button);
        back_upper_back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "BACK_UPPER_BACK";
                title.setText("Upper Back");
                dataController.setCurrentBodyLocation(bodyLocation);
                backBody.setImageResource(IMAGES[2]);
            }
        });

        //Back Lower Back
        Button back_lower_back_button = (Button) bodyView.findViewById(R.id.back_lower_back_button);
        back_lower_back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "BACK_LOWER_BACK";
                title.setText("Lower Back");
                dataController.setCurrentBodyLocation(bodyLocation);
                backBody.setImageResource(IMAGES[3]);
            }
        });

        //Back Right Arm
        Button back_right_arm_button = (Button) bodyView.findViewById(R.id.back_right_arm_button);
        back_right_arm_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "BACK_RIGHT_ARM";
                title.setText("Back Right Arm");
                dataController.setCurrentBodyLocation(bodyLocation);
                backBody.setImageResource(IMAGES[4]);
            }
        });

        //Back Left Arm
        Button back_left_arm_button = (Button) bodyView.findViewById(R.id.back_left_arm_button);
        back_left_arm_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "BACK_LEFT_ARM";
                title.setText("Back Left Arm");
                dataController.setCurrentBodyLocation(bodyLocation);
                backBody.setImageResource(IMAGES[5]);
            }
        });

        //Back Right Leg
        Button back_right_leg_button = (Button) bodyView.findViewById(R.id.back_right_leg_button);
        back_right_leg_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "BACK_RIGHT_LEG";
                title.setText("Back Right Leg");
                dataController.setCurrentBodyLocation(bodyLocation);
                backBody.setImageResource(IMAGES[6]);
            }
        });

        //Back Left Leg
        Button back_left_leg_button = (Button) bodyView.findViewById(R.id.back_left_leg_button);
        back_left_leg_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frontBody.setImageResource(IMAGES[8]);
                backBody.setImageResource(IMAGES[8]);
                bodyLocation = "BACK_LEFT_LEG";
                title.setText("Back Left Leg");
                dataController.setCurrentBodyLocation(bodyLocation);
                backBody.setImageResource(IMAGES[7]);
            }
        });
        return bodyView;
    }

}
