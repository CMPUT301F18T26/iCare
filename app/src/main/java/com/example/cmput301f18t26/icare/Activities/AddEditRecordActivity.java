package com.example.cmput301f18t26.icare.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.support.test.InstrumentationRegistry.getContext;

public class AddEditRecordActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {
    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private TextView dateStamp;
    private ImageView images;
    private Problem selectedProblem;
    private Fragment infoFragment;
    private Fragment geoFragment = new GeolocationFragment();
    private Fragment bodyFragment = new BodylocationFragment();

    SupportMapFragment sMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create a new instance of the support map fragment
        sMapFragment = SupportMapFragment.newInstance();
        

        Bundle extras = getIntent().getExtras();
        dataController = DataController.getInstance();
        selectedProblem = dataController.getSelectedProblem();

        setContentView(R.layout.activity_add_edit_record);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        sMapFragment.getMapAsync(this);


        infoFragment = new InfoFragment();
        loadFragment(infoFragment);//display Info Fragment By default - Tyler
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


    //called whenever one of the bottom nav buttons is selected
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();

        switch(menuItem.getItemId()){
            case R.id.info:
                fragment = infoFragment;
                break;

            case R.id.geo:
                fragment = sMapFragment;
                break;

            case R.id.body:
                fragment = bodyFragment;
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

    //ToDo on map resume stuff
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //when map is loaded do everything in here.

        //MapsInitializer.initialize(getContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        // Add a marker in Edmonton, and move the camera to the correct position.
        LatLng edmonton = new LatLng(53.5444, -113.4909);
        //Create the marker
        googleMap.addMarker(new MarkerOptions().position(edmonton).title("Edmonton Alberta"));
        //Set the initial camera zoom level
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        //Move the camera to the marker position
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(edmonton));

    }
}
