package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.cmput301f18t26.icare.R;

/**
 * Our main activity. This activity initializes with a launch screen where users may choose to
 * log in or sign up.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
        Intent intent = new Intent(this, PatientViewProblemListActivity.class);
        startActivity(intent);
         */
    }

    // Callback for the login button, go to login page
    public void login(View view) {
        //goes straight to patient view, for testing purposes
        Intent intent = new Intent(this, PatientViewProblemListActivity.class);
        startActivity(intent);
    }

    // Callback for the signup button, go to signup page
    public void signup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

}
