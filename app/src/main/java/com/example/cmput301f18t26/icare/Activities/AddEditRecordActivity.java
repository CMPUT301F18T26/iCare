package com.example.cmput301f18t26.icare.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;

public class AddEditRecordActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private TextView dateStamp;
    private ImageView images;
    private Problem selectedProblem;
    private Fragment infoFragment;
    private Fragment geoFragment;
    private Fragment bodyFragment;
    private FragmentManager fm;
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
    private LatLng currentLocation;
    private SupportMapFragment sMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Record");
        //Create a new instance of the support map fragment
        sMapFragment = SupportMapFragment.newInstance();
        sMapFragment.setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Bundle extras = getIntent().getExtras();
        dataController = DataController.getInstance();
        selectedProblem = dataController.getSelectedProblem();
        setContentView(R.layout.activity_add_edit_record);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        sMapFragment.getMapAsync(this);

        //Initialize all of the fragments
        infoFragment = new InfoFragment();
        geoFragment = new GeolocationFragment();
        bodyFragment = new BodyLocationFragment();

        //Add the fragments to the view using the fragment manager
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

        loadFragment(infoFragment); //display Info Fragment By default - Tyler
    }


    /**
     * Checks which fragment needs to be displayed, makes it visible and hides the others
     * @param fragment
     * @return
     */
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


    /**
     * Called whenever one of the bottom nav buttons is selected
     * Lets loadFragment know which fragment needs to be displayed
     * @param menuItem
     * @return
     */
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


    //https://stackoverflow.com/questions/45207709/how-to-add-marker-on-google-maps-android

    /**
     * onMapReady is required for any class that implements OnMapReadyCallback
     * This is where we adjust all of the settings for the map that is being displayed such as
     * type and checking for current location permission access. The view is adjusted accordingly
     * if permission is granted or not. The very first time permission to access location data
     * is asked for on a device the current location option should not show on the map.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
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
            map.setMyLocationEnabled(false);
            map.getUiSettings().setMyLocationButtonEnabled(false);
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                map.clear();

                // Animating to the touched position
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                map.addMarker(markerOptions);
                dataController.setCurrentGeoLocation(latLng);

            }
        });

        getCurrentLocation();


    }

    /**
     * Finds the current location of the user if permission has been granted.
     */
    public void getCurrentLocation(){
        try {
            if (mLocationPermissionGranted) {
                com.google.android.gms.tasks.Task<Location> locationResult = mFusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Location> task) {
                        lastKnownLocation = task.getResult();

                        if (task.isSuccessful()&&lastKnownLocation!=null) {

                            //set the current location to pass
                            currentLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                            // Set the map's camera position to the current location of the devices
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, DEFAULT_ZOOM));

                        } else {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
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
