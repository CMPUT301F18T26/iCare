package com.example.cmput301f18t26.icare.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
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
         * Lets check the old fashioned way whether our inputs are correct, they are simple
         * enough to not have to delegate to an object or controller
         */
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Error: Invalid login data",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * Check to see if this is a valid user by grabbing all users from our DataController
         * and looking for matching credentials
         */
        List<User> userList = dataController.getUsers();
        /**
         *  omg here we go with the algorithms... O(n) search ;)
         *
         *  remember to use .equals in java for strings!!!
         *  == is compare by reference, .equals is compare by value, implemented by the Object
         *
         *  there is some crazy stuff in Java where it may cache two identical strings as the same
         *  object if they are declared within the same block, simulating reference
         */
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                /**
                 * If matching user was found then give a notice for now, lets implement
                 * the subsequent views later, like what role the user is
                 */
                Toast.makeText(getApplicationContext(),
                        "User found!!!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        /**
         * If matching user was not found then give a notice
         */
        Toast.makeText(getApplicationContext(),
                "User not found",
                Toast.LENGTH_SHORT).show();
    }
}
