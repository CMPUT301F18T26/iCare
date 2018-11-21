package com.example.cmput301f18t26.icare.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.cmput301f18t26.icare.Controllers.SearchController;
import com.example.cmput301f18t26.icare.R;

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

    // Callback for the login button, go to login page
    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Callback for the signup button, go to signup page
    public void signup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    /**
     * This method checks to see if our app is connected to the internet for making decisions
     * about whether to use SearchController/ElasticSearch or to use the local cache in
     * DataController
     *
     * True = online
     */
    public static boolean checkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
