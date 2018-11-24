package com.example.cmput301f18t26.icare.Models;

import java.util.ArrayList;

/**
 * This class is a subclass of the records class. An object of this class is created when a user
 * wants to add a new record to their problems. recType = 0
 */
public class UserRecord extends BaseRecord {
    // Array list to store records for this user
    // Location is stored with lat and long, not Location
    private ArrayList<Integer> location;
    private ArrayList<String> photos;
    private String bodyLocation;

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
    public UserRecord(String date, String comment, String problemID, ArrayList<Integer> location, String bodyLocation, ArrayList<String> photos, String title){
        super(date, comment, problemID, title);
        super.setRecType(0);
        this.location = location;
        this.bodyLocation = bodyLocation;
        this.photos = photos;
    }

    public UserRecord(String date, String comment, String problemID, ArrayList<Integer> location, String bodyLocation, ArrayList<String> photos, int role, String title){
        super(date, comment, problemID, title);
        super.setRecType(role);
        this.location = location;
        this.bodyLocation = bodyLocation;
        this.photos = photos;
    }

    //Location Getters and Setters
    public ArrayList<Integer> getLocation(){return this.location;}

    public void setLocation(ArrayList<Integer> location){this.location = location;}

    //BodyLocation Getters and Setters
    public String getBodyLocation(){ return this.bodyLocation;}

    public void setBodyLocation(String bodyLocation){ this.bodyLocation = bodyLocation;}

    //Photos Getters and Setters
    public ArrayList<String> getPhotos(){ return this.photos = photos;}

}
