package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.SearchController;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEntry;
    private DataController dataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize all elements of view in our activity
        usernameEntry = findViewById(R.id.username_entry);

        // grab the instance of our DataController, it will lazy load it if not created elsewhere
        dataController = DataController.getInstance();
    }

    /**
     * Reloads all the details of a given person after this activity resumes.
     */
    @Override
    protected void onResume(){
        super.onResume();
        // Check if we have a user logged in before
        if (dataController.getCurrentUser() != null){
            // Setting the user name
            usernameEntry.setText(dataController.getCurrentUser().getUsername());
            // Calling the sign in function
            signin(findViewById(R.id.login_button));
        }
    }

    // Callback for the log in button, it attempts to log in the user
    public void signin(View view) {
        // grab the raw forms of our TEXT inputs
        String username = usernameEntry.getText().toString().trim();

        /*
         * Check if we have an internet connection
         *
         * Login and Signup are NOT supposed to work if you do not have an internet connection
         */
        boolean internetStatus =  dataController.checkInternet();
        if (!internetStatus) {
            Toast.makeText(getApplicationContext(),
                    "Error: No internet connection, login requires internet",
                    Toast.LENGTH_LONG).show();
            return;
        }

        /*
         * Lets check the old fashioned way whether our inputs are correct, they are simple
         * enough to not have to delegate to an object or controller
         */
        if (username.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Error: Invalid login data",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        User user = null;
        /*
         * Check DataController to see if User is valid
         */
        try {
            // grab the user from ES
            user = dataController.login(username);
            // go to either view Patient or view Problem screen depending on type of user logged in
            if (user.getRole() == 0) {
                Intent intent = new Intent(this, PatientViewProblemListActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ViewPatientsActivity.class);
                startActivity(intent);
            }

        } catch (Exception e) {
            Log.i("Error", "Could not find that User");
            Toast.makeText(getApplicationContext(),
                    "User not found",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
