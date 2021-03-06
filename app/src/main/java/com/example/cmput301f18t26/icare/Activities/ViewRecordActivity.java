package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static android.app.PendingIntent.getActivity;

public class ViewRecordActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {

    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private TextView dateStamp;
    private ImageView images;
    private Problem selectedProblem;
    SupportMapFragment sMapFragment;
    private GoogleMap map;
    private FragmentManager fm;
    private Fragment infoFragment;
    private Fragment geoFragment;
    private Fragment bodyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("View Record");
        //Bundle extras = getIntent().getExtras();
        dataController = DataController.getInstance();

        getSupportActionBar().setTitle("View Record");
        //Create a new instance of the support map fragment
        sMapFragment = SupportMapFragment.newInstance();
        sMapFragment.setHasOptionsMenu(false);


        setContentView(R.layout.activity_add_edit_record);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        sMapFragment.getMapAsync(this);
        infoFragment = new ViewInfoFragment();
        geoFragment = new ViewGeolocationFragment();
        bodyFragment = new ViewBodylocationFragment();

        fm = getSupportFragmentManager();
        fm
                .beginTransaction()
                .add(R.id.fragment_container, infoFragment)
                .commitNow();
        fm
                .beginTransaction()
                .add(R.id.fragment_container, sMapFragment)
                .commitNow();
        fm
                .beginTransaction()
                .add(R.id.fragment_container, bodyFragment)
                .commitNow();

       // Fragment ViewInfoFragment = new Fragment();

        loadFragment(infoFragment);//display View Info Fragment By default - Tyler
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            if(fragment==infoFragment)
                fm
                        .beginTransaction()
                        .show(infoFragment)
                        .hide(sMapFragment)
                        .hide(bodyFragment)
                        .commitNow();
            if(fragment==sMapFragment)
                fm
                        .beginTransaction()
                        .show(sMapFragment)
                        .hide(infoFragment)
                        .hide(bodyFragment)
                        .commitNow();
            if(fragment==bodyFragment)
                fm
                        .beginTransaction()
                        .show(bodyFragment)
                        .hide(sMapFragment)
                        .hide(infoFragment)
                        .commitNow();
            return true;
        }
        return false;
    }


    //called whenever one of the bottom nav buttons is selected - tyler
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        if (dataController.getSelectedRecord().getRecType() == 0) {
            //track which object is clicked - tyler
            switch (menuItem.getItemId()) {
                case R.id.info: //clicking the info button in bottom nav
                    fragment = infoFragment; // creates a new ViewInfoFragment
                    break;

                case R.id.geo:
                    fragment = sMapFragment;
                    break;

                case R.id.body:
                    fragment = bodyFragment;
                    break;
            }
        } else {
            //track which object is clicked - tyler
            switch (menuItem.getItemId()) {
                case R.id.info: //clicking the info button in bottom nav
                    Toast.makeText(getApplicationContext(), "This is a doctor's comment, this option is not available on this type of record.", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.geo:
                    Toast.makeText(getApplicationContext(), "This is a doctor's comment, this option is not available on this type of record.", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.body:
                    Toast.makeText(getApplicationContext(), "This is a doctor's comment, this option is not available on this type of record.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        return loadFragment(fragment);
    }

    // This activity is being stopped, saving data to file
    @Override
    public void onStop() {
        super.onStop();
        // Writing to file
        dataController.writeDataToFiles(getApplicationContext());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        BaseRecord selectedRecord = dataController.getSelectedRecord();
        map = googleMap;

        if (UserRecord.class == selectedRecord.getClass()) {
            // Getting the record as a user record
            UserRecord userRecord = (UserRecord) selectedRecord;
            //Find the bodyLocation
            LatLng location = userRecord.getLocation();
            String title = userRecord.getTitle();
            //add the marker to the map
            //Create the marker
            if (location != null) {
                map.addMarker(new MarkerOptions().position(location).title(title));
                //Set the initial camera zoom level
                map.moveCamera(CameraUpdateFactory.zoomTo(14));
                //Move the camera to the marker position
                map.moveCamera(CameraUpdateFactory.newLatLng(location));
            }
        }
    }
}
