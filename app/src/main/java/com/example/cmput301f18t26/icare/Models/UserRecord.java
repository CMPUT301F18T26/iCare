package com.example.cmput301f18t26.icare.Models;

import android.graphics.Bitmap;
import android.media.Image;

import com.example.cmput301f18t26.icare.BodyLocation;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a subclass of the records class. An object of this class is created when a user
 * wants to add a new record to their problems. recType = 0
 */
public class UserRecord extends BaseRecord {
    // Array list to store records for this user
    // Location is stored with lat and long, not Location
    private LatLng location;
    private List<String> photos;
    private BodyLocation bodyLocation;

    /**
     * The constructor for this class.
     * @param date
     * @param comment
     * @param problemID
     * @param location
     * @param bodyLocation
     * @param photos
     * @param title
     */
    public UserRecord(String date, String comment, String problemID, LatLng location, BodyLocation bodyLocation, List<String> photos, String title){
        super(date, comment, problemID, title);
        super.setRecType(0);
        this.location = location;
        this.bodyLocation = bodyLocation;
        this.photos = photos;
    }

    public UserRecord(String date, String comment, String problemID, LatLng location, BodyLocation bodyLocation, List<String> photos, int role, String title){
        super(date, comment, problemID, title);
        super.setRecType(role);
        this.location = location;
        this.bodyLocation = bodyLocation;
        this.photos = photos;
    }

    //Location Getters and Setters
    public LatLng getLocation(){return this.location;}

    public void setLocation(LatLng location){this.location = location;}

    //BodyLocation Getters and Setters
    public BodyLocation getBodyLocation(){ return this.bodyLocation;}

    public void setBodyLocation(BodyLocation bodyLocation){ this.bodyLocation = bodyLocation;}

    //Photos Getters and Setters
    public List<String> getPhotos(){ return this.photos = photos;}

}
