package com.example.cmput301f18t26.icare.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

public class EditProfileActivity extends AppCompatActivity {
    private User currentUser;
    private DataController dataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Getting the data controller and storing it
        this.dataController = DataController.getInstance();
        // Getting the currentUser
        this.currentUser = this.dataController.getCurrentUser();
        // Showing the UI
        setContentView(R.layout.activity_edit_your_profile);
        // Now getting all the view and changing their info
        TextView username = findViewById(R.id.profile_name);
        username.setText(currentUser.getUsername());
        final EditText email= findViewById(R.id.email);
        email.setText(currentUser.getEmail());
        final EditText phone = findViewById(R.id.phone);
        phone.setText(currentUser.getPhone());

        // Not setting up the on-click listener on the save button, save_profile
        Button editButton = findViewById(R.id.save_profile);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Just save the new data
                currentUser.setEmail(email.getText().toString());
                currentUser.setPhone(phone.getText().toString());
                try {
                    // Trying to update the user on ES
                    dataController.updateElasticSearchForNewUserInfo(currentUser);
                    // Change was successful
                    dataController.setCurrentUser(currentUser);
                    Toast.makeText(getApplicationContext(),
                            "Profile Info Changed.",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),
                            "An error occured while trying to change profile info on the server.",
                            Toast.LENGTH_SHORT).show();
                }
                // Now we end the activity
                finish();
            }
        });
    }
}
