package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.SearchController;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEntry;
    private EditText passwordEntry;
    private DataController dataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize all elements of view in our activity
        usernameEntry = findViewById(R.id.username_entry);
        passwordEntry = findViewById(R.id.password_entry);

        // grab the instance of our DataController, it will lazy load it if not created elsewhere
        dataController = DataController.getInstance();
    }

    // Callback for the log in button, it attempts to log in the user
    public void signin(View view) {
        // grab the raw forms of our TEXT inputs
        String username = usernameEntry.getText().toString().trim();
        String password = passwordEntry.getText().toString().trim();

        /**
         * Check if we have an internet connection
         *
         * Login and Signup are NOT supposed to work if you do not have an internet connection
         */
        if (!MainActivity.checkConnection()) {
            Toast.makeText(getApplicationContext(),
                    "Error: No internet connection, login requires internet",
                    Toast.LENGTH_LONG).show();
            return;
        }

        /**
         * Lets check the old fashioned way whether our inputs are correct, they are simple
         * enough to not have to delegate to an object or controller
         */
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Error: Invalid login data",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        User user = null;
        /**
         * Check DataController to see if User is valid
         */
        try {
            // grab the user from ES
            user = dataController.login(username, password);

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
