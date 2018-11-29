package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.example.cmput301f18t26.icare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class RecordMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DataController dataController;
    SupportMapFragment sMapFragment;
    private GoogleMap map;
    Problem selectedProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_map);
        dataController = DataController.getInstance();
        selectedProblem = dataController.getSelectedProblem();
        sMapFragment = SupportMapFragment.newInstance();
        //sMapFragment.setHasOptionsMenu(true);


        sMapFragment.getMapAsync(this);

        loadFragment(sMapFragment);
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_map_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        List<BaseRecord> allRecords =  dataController.getRecords(selectedProblem);
        List<LatLng> locations;

        for (BaseRecord each:allRecords){
            // Now getting the bodyLocation if the class is a UserRecord
            if (UserRecord.class == each.getClass()) {
                // Getting the record as a user record
                UserRecord userRecord = (UserRecord) each;
                //Find the bodyLocation
                LatLng location = userRecord.getLocation();
                String title = userRecord.getTitle();
                //add the marker to the map
                //Create the marker
                if(location != null) {
                    map.addMarker(new MarkerOptions().position(location).title(title));
                    //Set the initial camera zoom level
                    map.moveCamera(CameraUpdateFactory.zoomTo(14));
                    //Move the camera to the marker position
                    map.moveCamera(CameraUpdateFactory.newLatLng(location));
                }
            }


        }

    }
}