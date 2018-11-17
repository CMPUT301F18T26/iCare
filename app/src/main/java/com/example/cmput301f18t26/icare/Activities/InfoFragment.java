package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.R;

public class InfoFragment extends Fragment{
    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private TextView dateStamp;
    //private Calendar cal;
    private String problemUID;
    private ImageView images;
    private String strdate = "apple";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        Log.d("tyler","get here1");
        titleEntry = (EditText) rootView.findViewById(R.id.record_title);
        descriptionEntry = (EditText) rootView.findViewById(R.id.record_comment);
        dateStamp =  rootView.findViewById(R.id.record_date_and_time);
        dateStamp.setText(strdate);




        return rootView;
    }
}
