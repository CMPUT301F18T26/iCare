package com.example.cmput301f18t26.icare.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.SearchController;
import com.example.cmput301f18t26.icare.Models.ImageAsString;
import com.example.cmput301f18t26.icare.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Our main activity. This activity initializes with a launch screen where users may choose to
 * log in or sign up.
 */
public class MainActivity extends AppCompatActivity {
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate Jest
        SearchController.setup();

        // Get our App context
        context = this.getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // So the user doesn't mess with the app while syncing
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        // Need to get data in from file
        DataController.getInstance().readDataFromFiles(context);

        // Sending data to server
        DataController.getInstance().sendDataToServer();

        // User can now mess with app
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        // Now we need to read the data from file
        // Now we check if there was a user already logged in
        if (DataController.getInstance().getCurrentUser() != null) {
            // Telling the system to go to the login page
            DataController.getInstance().login();

            if (DataController.getInstance().getCurrentUser().getRole() == 0) {
                Intent intent = new Intent(this, PatientViewProblemListActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ViewPatientsActivity.class);
                startActivity(intent);
            }
        }

        // Taking progress bar away
        findViewById(R.id.progress_bar).setVisibility(View.GONE);
        // Bringing back layout
        findViewById(R.id.icare_main_screen).setVisibility(View.VISIBLE);
        findViewById(R.id.login_button).setVisibility(View.VISIBLE);
        findViewById(R.id.signup_button).setVisibility(View.VISIBLE);
    }

    /**
     * Callback for the login button, go to login page
     * @param view
     */
    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Callback for the signup button, go to signup page
     * @param view
     */
    public void signup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
