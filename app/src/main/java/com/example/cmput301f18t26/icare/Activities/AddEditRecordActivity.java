package com.example.cmput301f18t26.icare.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;


import static android.support.test.InstrumentationRegistry.getContext;

public class AddEditRecordActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private TextView dateStamp;
    private ImageView images;
    private Problem selectedProblem;
    private Fragment infoFragment;
    private Fragment geoFragment = new GeolocationFragment();
    private Fragment bodyFragment = new BodylocationFragment();
    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location lastKnownLocation;
    private GoogleMap map;
    private static final int DEFAULT_ZOOM = 14;
    private LatLng defaultLocation = new LatLng(53.509,-113.5);
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private CameraPosition cameraPosition;

    SupportMapFragment sMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("Add Record");
        getSupportActionBar().setTitle("Add Record");
        //Create a new instance of the support map fragment
        sMapFragment = SupportMapFragment.newInstance();
        sMapFragment.setHasOptionsMenu(true);

        //TODO on resume custom method for the instance fragment
        //sMapFragment.onResume();

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Creating the menu options from the xml file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_current_location, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // set/save the geolocation of the record
        Toast.makeText(getApplicationContext(),"Your location has been added to the record",Toast.LENGTH_SHORT).show();
        return true;
    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
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

        switch (menuItem.getItemId()) {
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
//    @Override
//    protected void onResume() {
//        super.onResume();
//        getCurrentLocation();
//    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //when map is loaded do everything in here.
        map = googleMap;
        //MapsInitializer.initialize(getContext());
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        }

        // Add a marker in Edmonton, and move the camera to the correct position.
        LatLng edmonton = new LatLng(53.5444, -113.4909);
        //Create the marker
        map.addMarker(new MarkerOptions().position(edmonton).title("Edmonton Alberta"));
        //Set the initial camera zoom level
        map.moveCamera(CameraUpdateFactory.zoomTo(10));
        //Move the camera to the marker position
        map.moveCamera(CameraUpdateFactory.newLatLng(edmonton));

    }


    public void getCurrentLocation(){
        try {
            if (mLocationPermissionGranted) {
                com.google.android.gms.tasks.Task<Location> locationResult = mFusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Location> task) {
                        lastKnownLocation = task.getResult();
                        if (task.isSuccessful()&&lastKnownLocation!=null) {

                            // Set the map's camera position to the current location of the devices
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                        } else {
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
            else{
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,DEFAULT_ZOOM));
                map.getUiSettings().setMyLocationButtonEnabled(false);
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }
}
