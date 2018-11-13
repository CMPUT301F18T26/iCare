package com.example.cmput301f18t26.icare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ProblemListActivity extends AppCompatActivity {

    private ListView oldProblemList;
    private ArrayAdapter<Problem> adapter;
    private ArrayList<Problem> problemList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_condition_view_list_record_item);


        oldProblemList = (ListView) findViewById(R.id.ProblemListView);
        //Takes you to edit emotion details when an emotion on list view is clicked
        oldProblemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = oldProblemList.getItemAtPosition(position);
                Problem problem = Problem.class.cast(object);
                String SelectedEmotion = problem.getId();
                //Intent i = new Intent(view.getContext(), RecordListView.class);
                /*
                i.putExtra("SelectedEmotion", SelectedEmotion);
                i.putExtra("Emotion", emotion);
                i.putExtra("Index", position);
                startActivity(i);
                */
            }
        });
    }


    protected void onStart() {
        super.onStart();
        //loadFromFile();
        //sort();
        adapter = new ArrayAdapter<Problem>(this,
                R.layout.list_item, problemList);
        adapter.notifyDataSetChanged();
        oldProblemList.setAdapter(adapter);
    }
}
