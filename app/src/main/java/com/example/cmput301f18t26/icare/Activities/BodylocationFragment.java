package com.example.cmput301f18t26.icare.Activities;

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

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.R;

public class BodylocationFragment extends Fragment{

    private DataController dataController;
    private TextView title;
    private String bodyLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View bodyView = inflater.inflate(R.layout.fragment_bodylocation, null);
        dataController = DataController.getInstance();

        //Title of the page
        title = (TextView) bodyView.findViewById(R.id.patient_record_body_label_location);

        //Front head -- seems like this will need to be repeated for every enum option
        Button front_head_button = (Button) bodyView.findViewById(R.id.front_head_button);
        front_head_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bodyLocation = "FRONT_HEAD";
                title.setText("Front Head");
                dataController.setCurrentBodyLocation(bodyLocation);
            }
        });

        return bodyView;
    }
}
