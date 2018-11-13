package com.example.cmput301f18t26.icare.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.UserFactory;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.R;

public class SignupActivity extends AppCompatActivity {
    private EditText usernameEntry;
    private EditText passwordEntry;
    private EditText phoneEntry;
    private EditText emailEntry;
    private RadioGroup roleSelect;
    private RadioButton roleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // initialize all elements of view in our activity
        usernameEntry = findViewById(R.id.username_entry);
        passwordEntry = findViewById(R.id.password_entry);
        phoneEntry = findViewById(R.id.phone_entry);
        emailEntry = findViewById(R.id.email_entry);
        roleSelect = findViewById(R.id.role_entry);
    }

    // Callback for the signup button, it attempts to create the user
    public void register(View view) {
        // grab the raw forms of our TEXT inputs
        String username = usernameEntry.getText().toString().trim();
        String password = passwordEntry.getText().toString().trim();
        String phone = phoneEntry.getText().toString().trim();
        String email = emailEntry.getText().toString().trim();
        /**
         * grab selected button from radio group and use that buttons index to fetch the role
         *
         * THIS IS A HUGE HACK, THIS COULD BE REFACTORED BY USING ENUMS PROPERLY BUT WE WILL SAVE
         * THAT FOR LATER - if anybody wants to be a hero
         */
        int roleEntrySelectedId = roleSelect.getCheckedRadioButtonId();
        roleButton = findViewById(roleEntrySelectedId);
        int role = roleSelect.indexOfChild(roleButton);

        /**
         * Lets attempt to construct a user out of the data we are provided on the signup page.
         *
         * Users are a great way to use the Factory pattern as we have a User superclass with
         * two subclasses that we could instantiate here based on input.
         *
         * Another great pattern to use here is to defer validation checks to the user class/object
         * itself, refer to the user class to see how this is done. Notice we will not need to
         * review the inputs line by line here to detect errors, or anywhere else.
         */
        User user = UserFactory.getUser(username, password, email, phone, role);

        if (user.validate() == false) {
            Toast.makeText(getApplicationContext(),
                    "Error: Invalid data",
                    Toast.LENGTH_SHORT).show();
        }
        
    }
}
