package com.example.cmput301f18t26.icare.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
        // grab button from radio group and grab text of that button to get role
        int roleEntrySelectedId = roleSelect.getCheckedRadioButtonId();
        roleButton = findViewById(roleEntrySelectedId);
        String role = roleButton.getText().toString();
    }
}
