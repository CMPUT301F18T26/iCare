package com.example.cmput301f18t26.icare;

import android.location.Location;
import java.util.Calendar;
import java.util.ArrayList;

public class UserRecord extends Record {
    // Array list to store records for this user
    private String recordId;

    private Location location;
    private ArrayList<String> photos;

    //needs to change type once conor implemts the class
    private String bodyLocation;

    UserRecord(String title, Calendar date, String comment, Location location, String bodyLocation, ArrayList<String> photos){
        super(title, date, comment);
        this.location = location;
        this.bodyLocation = bodyLocation;
        this.photos = new ArrayList<String>();

    }

    //Location Getters and Setters
    public Location getLocation(){return this.location;}

    public void setLocation(Location location){this.location = location;}

    //BodyLocation Getters and Setters
    public String getBodyLocation(){ return this.bodyLocation;}

    public void setBodyLocation(String bodyLocation){ this.bodyLocation = bodyLocation;}

    //Photos Getters and Setters
    public ArrayList<String> getPhotos(){ return this.photos = photos;}

    public void setPhotos(){ this.photos = photos;}

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }


}
