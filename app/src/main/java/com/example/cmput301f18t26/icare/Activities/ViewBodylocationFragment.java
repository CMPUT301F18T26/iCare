package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private ImageView frontBody;
    private ImageView backBody;
    int[] IMAGES ={R.drawable.head_selected,
            R.drawable.shoulders,
            R.drawable.torso,
            R.drawable.right_arm,
            R.drawable.left_arm,
            R.drawable.right_leg,
            R.drawable.left_leg};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bodyView = inflater.inflate(R.layout.fragment_view_bodylocation, null);
        dataController = DataController.getInstance();
        frontBody = (ImageView) bodyView.findViewById(R.id.front_body_location_photo);
        backBody = (ImageView) bodyView.findViewById(R.id.back_body_location_photo);

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

        //Takes your selection and reflects it in the header and the stick figure display
        if (bodyLocation != null){
            bodyLocationString = bodyLocation.getBodyLocation();
            title.setText(bodyLocationString);
            setBodyImage(bodyLocationString);
        }
        return bodyView;
    }

    /**
     * Takes the string of your body location selection and reflects your selection
     * on the stick figure display.
     * @param bodyLocation
     */
    public void setBodyImage(String bodyLocation){
        switch(bodyLocation){
            //Front of the body
            case "Front Head":
                frontBody.setImageResource(IMAGES[0]);
                break;
            case "Front Neck and Shoulders":
                frontBody.setImageResource(IMAGES[1]);
                break;
                //Waiting for new images
                /*
                case "Chest":
                    frontBody.setImageResource(IMAGES[XX]);
                    break;
                case "Stomach":
                    frontBody.setImageResource(IMAGES[XX]);
                    break;
                    */
            case "Front Right Arm":
                frontBody.setImageResource(IMAGES[3]);
                break;
            case "Front Left Arm":
                frontBody.setImageResource(IMAGES[4]);
                break;
            case "Front Right Leg":
                frontBody.setImageResource(IMAGES[5]);
                break;
            case "Front Left Leg":
                frontBody.setImageResource(IMAGES[6]);
                break;

            //Back of the body
            case "Back Head":
                backBody.setImageResource(IMAGES[0]);
                break;
            case "Back Neck and Shoulders":
                backBody.setImageResource(IMAGES[1]);
                break;
                /*
                case "Upper Back":
                    backBody.setImageResource(IMAGES[XX]);
                    break;
                case "Lower Back":
                    backBody.setImageResource(IMAGES[XX]);
                    break;
                    */
            case "Back Right Arm":
                backBody.setImageResource(IMAGES[3]);
                break;
            case "Back Left Arm":
                backBody.setImageResource(IMAGES[4]);
                break;
            case "Back Right Leg":
                backBody.setImageResource(IMAGES[5]);
                break;
            case "Back Left Leg":
                backBody.setImageResource(IMAGES[6]);
                break;
        }
    }
}
