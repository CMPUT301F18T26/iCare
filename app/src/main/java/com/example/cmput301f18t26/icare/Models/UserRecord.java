package com.example.cmput301f18t26.icare.Models;

import android.location.Location;

import com.example.cmput301f18t26.icare.BodyLocation;

import java.util.Calendar;
import java.util.ArrayList;

public class UserRecord extends Record {
    // Array list to store records for this user
    private Location location;
    private ArrayList<String> photos;
    private BodyLocation bodyLocation;

    public UserRecord(String title, String date, String comment, String problemID, Location location, BodyLocation bodyLocation, ArrayList<String> photos){
        super(title, date, comment, problemID);
        this.location = location;
        this.bodyLocation = bodyLocation;
        this.photos = new ArrayList<String>();

    }

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

    public void addPhoto(String photo){ this.photos.add(photo);}

    public void removePhoto(Object photo){ this.photos.remove(photo);}

}
