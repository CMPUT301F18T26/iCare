package com.example.cmput301f18t26.icare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Saves all entered values to the user profile
        Button saveButton = (Button) findViewById(R.id.signup_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                saveValues();
            }
        });
    }

    public void saveValues(){
        int MAX_USERNAME_LEN = 25;
        int MAX_PASSWORD_LEN = 25;
        int MAX_EMAIL_LEN = 25;
        int PHONE_NUM_LEN = 10;

        //Save username
        EditText usernameEntry = (EditText) findViewById(R.id.username_entry);
        String username = usernameEntry.getText().toString();
        if (username.length() <= MAX_USERNAME_LEN){
            //user.setUsername(username)
        }
        else{
            Toast.makeText(
                    SignUpActivity.this,
                    "Maximum username length is 25",
                    Toast.LENGTH_LONG
            ).show();
        }

        //Save password
        EditText passwordEntry = (EditText) findViewById(R.id.password_entry);
        String password = passwordEntry.getText().toString();
        if (password.length() <= MAX_PASSWORD_LEN){
            //user.setPassword(password);
        }
        else{
            Toast.makeText(
                    SignUpActivity.this,
                    "Maximum password length is 25",
                    Toast.LENGTH_LONG).show();
        }

        //Save phone number
        EditText phoneEntry = (EditText) findViewById(R.id.phone_entry);
        String phoneNum = phoneEntry.getText().toString();
        //String being only numbers taken care of in xml file
        if (phoneNum.length() == PHONE_NUM_LEN){
            //user.setPhoneNum(phoneNum);
        }
        else{
            Toast.makeText(
                    SignUpActivity.this,
                    "Entry must be a 10 digit string with only numbers, ie 0123456789",
                    Toast.LENGTH_LONG).show();
        }

        //Save email
        EditText emailEntry = (EditText) findViewById(R.id.email_entry);
        String email = emailEntry.getText().toString();
        if (email.length() <= MAX_EMAIL_LEN){
            //user.setEmail(email);
        }
        else{
            Toast.makeText(
                    SignUpActivity.this,
                    "Maximum email length is 25",
                    Toast.LENGTH_LONG).show();
        }

        //Used below link to find how to use Radio Buttons
        //https://www.dev2qa.com/android-radio-button-example/
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int id = radioGroup.getCheckedRadioButtonId();
        if ( id == -1) {
            Toast.makeText(
                    SignUpActivity.this,
                    "Please make a User Type selection",
                    Toast.LENGTH_LONG).show();
        }
        else {
            // Get user selected RadioButton object by id.
            RadioButton radioButton = (RadioButton) findViewById(id);
            // Get the RadioButton text.
            String userSelection = (String) radioButton.getText();
            //user.setType(userSelection);
        }
    }
}
