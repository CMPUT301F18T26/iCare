package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.R;

public class ViewSingleUseCodeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_use_code);

        // Get the view where we show the code
        TextView textView = findViewById(R.id.single_use_code);

        // Changing the value to what was passed in
        Intent intent = getIntent();
        String singleUseCode = intent.getExtras().getString("single_use_code");
        textView.setText(singleUseCode);
    }
}
