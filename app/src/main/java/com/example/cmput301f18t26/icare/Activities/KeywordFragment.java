package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.example.cmput301f18t26.icare.R;

public class KeywordFragment extends Fragment {
    private DataController dataController;

    private EditText keywordText;
    private RadioGroup searchSelect;
    private ImageButton search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataController = DataController.getInstance();
    }

    @Override
    public void onPause(){
        super.onPause();
        Bundle savedInstanceState = new Bundle();
        onSaveInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_by_keyword, container, false);

        //Get everything we need for the View
        keywordText = rootView.findViewById(R.id.edit_text_search_by_keyword);
        search = rootView.findViewById(R.id.search);

        //Saves your Record and returns you to the Record List View
        search = rootView.findViewById(R.id.search_by_keyword_button);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (keywordText.getText().length() == 0) {
                    Toast.makeText( getActivity(),
                            "Please enter a search term!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    dataController.searchByKeyword(keywordText.getText().toString());
                    Intent intent = new Intent(getContext(), ViewSearchResultsActivity.class);
                    startActivity(intent);
                }

            }

        });

        return rootView;
    }


}
