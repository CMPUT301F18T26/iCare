package com.example.cmput301f18t26.icare.Models;

import com.example.cmput301f18t26.icare.BodyLocation;

import java.util.ArrayList;

/**
 * This class is a subclass of the records class. An object of this class is created when a user
 * wants to add a new record to their problems. recType = 1
 */
public class UserRecord extends BaseRecord {
    // Array list to store records for this user
    // Location is stored with lat and long, not Location
    private ArrayList<Integer> location;
    private ArrayList<String> photos;
    private String bodyLocation;

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
    public UserRecord(String title, String date, String comment, String problemID, ArrayList<Integer> location, String bodyLocation, ArrayList<String> photos, int recordType){
        super(title, date, comment, problemID, recordType);
        this.location = location;
        this.bodyLocation = bodyLocation;
        this.photos = photos;
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
    public ArrayList<Integer> getLocation(){return this.location;}

    public void setLocation(ArrayList<Integer> location){this.location = location;}

    //BodyLocation Getters and Setters
    public String getBodyLocation(){ return this.bodyLocation;}

    public void setBodyLocation(String bodyLocation){ this.bodyLocation = bodyLocation;}

    //Photos Getters and Setters
    public ArrayList<String> getPhotos(){ return this.photos = photos;}

}
