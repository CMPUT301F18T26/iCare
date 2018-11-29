package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewRecordActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {

    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private TextView dateStamp;
    private ImageView images;
    private Problem selectedProblem;
    SupportMapFragment sMapFragment;
    private GoogleMap map;

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

        Fragment ViewInfoFragment = new Fragment();

        loadFragment(new ViewInfoFragment());//display View Info Fragment By default - Tyler
    }


    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }


    //called whenever one of the bottom nav buttons is selected - tyler
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        //track which object is clicked - tyler
        switch(menuItem.getItemId()){
            case R.id.info: //clicking the info button in bottom nav
                fragment = new ViewInfoFragment(); // creates a new ViewInfoFragment
                break;

            case R.id.geo:
                fragment = sMapFragment;
                break;

            case R.id.body:
                fragment = new ViewBodylocationFragment();
                break;
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

        LatLng recordLocation = dataController.getCurrentGeoLocation();
        BaseRecord selectedRecord = dataController.getSelectedRecord();
        String mapLabel = selectedRecord.getTitle();
        map = googleMap;

        if(recordLocation != null) {
            //Create the marker
            map.addMarker(new MarkerOptions().position(recordLocation).title(mapLabel));
            //Set the initial camera zoom level
            map.moveCamera(CameraUpdateFactory.zoomTo(14));
            //Move the camera to the marker position
            map.moveCamera(CameraUpdateFactory.newLatLng(recordLocation));
        }
    }
}
