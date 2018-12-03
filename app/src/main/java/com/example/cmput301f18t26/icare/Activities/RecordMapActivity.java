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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class RecordMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DataController dataController;
    SupportMapFragment sMapFragment;
    private GoogleMap map;
    Problem selectedProblem;
    private ArrayList<Marker> markers = new ArrayList<Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_map);
        dataController = DataController.getInstance();
        selectedProblem = dataController.getSelectedProblem();
        sMapFragment = SupportMapFragment.newInstance();
        //sMapFragment.setHasOptionsMenu(true);
        getSupportActionBar().setTitle("All Record Locations");


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

    /**
     * Checks for all of the record locations and displays them on the map. Then finds
     * the max and min lat and long and adjusts the zoom of the camera appropriately.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        int padding = 200; // offset from edges of the map in pixels

        List<BaseRecord> allRecords =  dataController.getRecords(selectedProblem);
        List<LatLng> locations;

        for (BaseRecord each:allRecords){
            // Now getting the location if the class is a UserRecord
            if (UserRecord.class == each.getClass()) {
                // Getting the record as a user record
                UserRecord userRecord = (UserRecord) each;
                //Find the location
                LatLng location = userRecord.getLocation();
                String title = userRecord.getTitle();
                //add the marker to the map
                //Create the marker
                if(location != null) {
                    //create marker and add marker to the map
                    Marker marker = map.addMarker(new MarkerOptions().position(location).title(title));
                    //add each marker to a list of all markers
                    markers.add(marker);
                }

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                googleMap.animateCamera(cu);
                
            }


        }

    }
}
