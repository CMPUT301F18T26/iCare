package com.example.cmput301f18t26.icare.Models;

import android.location.Location;

import com.example.cmput301f18t26.icare.BodyLocation;

import java.util.Calendar;
import java.util.ArrayList;

/**
 * This class is a subclass of the records class. An object of this class is created when a user
 * want to add a new record to their problems.
 */
public class UserRecord extends Record {
    // Array list to store records for this user
    private Location location;
    private ArrayList<String> photos;
    private BodyLocation bodyLocation;

    /**
     * The constructor for this class.
     * @param title
     * @param date
     * @param comment
     * @param problemID
     * @param location
     * @param bodyLocation
     * @param photos
     */
    public UserRecord(String title, String date, String comment, String problemID, Location location, BodyLocation bodyLocation, ArrayList<String> photos){
        super(title, date, comment, problemID);
        this.location = location;
        this.bodyLocation = bodyLocation;
        this.photos = new ArrayList<String>();

    }

    /**
     * When called, returns a string with the title of the record and the date.
     * @return
     */
    public String toString(){
        //http://www.ntu.edu.sg/home/ehchua/programming/java/DateTimeCalendar.html
        String date = super.getDate();

        return super.getTitle() + "\n" + date;
    }

    //Location Getters and Setters
    public Location getLocation(){return this.location;}

    public void setLocation(Location location){this.location = location;}

    //BodyLocation Getters and Setters
    public BodyLocation getBodyLocation(){ return this.bodyLocation;}

    public void setBodyLocation(BodyLocation bodyLocation){ this.bodyLocation = bodyLocation;}

    //Photos Getters and Setters
    public ArrayList<String> getPhotos(){ return this.photos = photos;}

    /**
     * Adds a new photo to the list of photos that are stored internally.
     * @param photo
     */
    public void addPhoto(String photo){ this.photos.add(photo);}

    /**
     * Removes a particular photo from the list of photos stores internally.
     * @param photo
     */
    public void removePhoto(Object photo){ this.photos.remove(photo);}

}
