package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

/**
 * Activity was created to the profile of a user.
 */
public class ViewProfileActivity extends AppCompatActivity {
    private User currentUser;
    private User userToDisplay;
    private DataController dataController;

    /**
     * When called, it first checks the user_id of the user passed in. If the if of currentUser and userToDisplay is the same then
     * R.layout.activity_view_your_profile otherwise activity_care_provider_view_patient_profile is loaded.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Getting the data controller and storing it
        this.dataController = DataController.getInstance();
        // Getting the currentUser
        this.currentUser = this.dataController.getCurrentUser();
        // Getting the intent so we can get extra info
        Intent intent = getIntent();

        // Now we compare to see if if the user_id passed in and this.currentUser's id is the same.
        if (this.currentUser.getUID().equals(intent.getExtras().getString("user_id"))) {
            // Setting the current user
            this.userToDisplay = currentUser;
            // Launching the layout
            setContentView(R.layout.activity_view_your_profile);
        } else {
            // Setting the user to display user after getting info from elastic search
            this.userToDisplay = User.fetchPatientInformation(intent.getExtras().getString("user_id"));
            // Launching the layout
            setContentView(R.layout.activity_care_provider_view_patient_profile);
        }

        // Setting the user name
        TextView username = findViewById(R.id.profile_name);
        username.setText(userToDisplay.getUsername());
        // Setting the email
        TextView email = findViewById(R.id.email);
        email.setText(userToDisplay.getEmail());
        // Setting the phone-number
        TextView phone = findViewById(R.id.phone);
        phone.setText(userToDisplay.getPhone());


        if (this.currentUser.getUID().equals(this.userToDisplay.getUID())){
            // Creates a new activity called EditProfileActivity
            Button editButton = findViewById(R.id.edit_profile);
            editButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (DataController.getInstance().checkInternet()) {
                        // Creating the intent to start EditProfileActivity and starting it
                        Intent newActivity = new Intent(ViewProfileActivity.this, EditProfileActivity.class);
                        startActivity(newActivity);
                    } else {
                        Toast.makeText(getApplicationContext(), "You can only perform this action online.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Reloads all the details of a given person after this activity resumes.
     */
    @Override
    protected void onResume(){
        super.onResume();
        // Just need to update the user information in case it was changed
        // Setting the user name
        TextView username = findViewById(R.id.profile_name);
        username.setText(userToDisplay.getUsername());
        // Setting the email
        TextView email = findViewById(R.id.email);
        email.setText(userToDisplay.getEmail());
        // Setting the phone-number
        TextView phone = findViewById(R.id.phone);
        phone.setText(userToDisplay.getPhone());
    }

    // This activity is being stopped, saving data to file
    @Override
    public void onStop() {
        super.onStop();
        // Writing to file
        dataController.writeDataToFiles(getApplicationContext());
    }
}
