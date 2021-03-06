package com.example.cmput301f18t26.icare;

import android.location.Location;

import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Calendar;
import static org.junit.Assert.*;

public class UserRecordTest {

    @Test
    public void testNewRecordConstructor() {
//        // Loading up the values to test
        String title = "This is a Title";
        String date = Calendar.getInstance().toString(); // gets the current time.
        String comment = "This is a comment";
        LatLng location = new LatLng(20.914332, 162.191602);
        BodyLocation bodyLocation = BodyLocation.FRONT_CHEST;
        ArrayList<String> photos = new ArrayList<>();

        UserRecord record = new UserRecord(date, comment, "123", location, bodyLocation, photos, title);

        // Check if record ID is instantiated correctly
        //assertEquals(record.getRecordId(), id);

        assertEquals(title, record.getTitle());
        assertEquals(date, record.getDate());
        assertEquals(comment, record.getComment());
        assertEquals(location, record.getLocation());
        assertEquals(bodyLocation, record.getBodyLocation());
        assertEquals(photos, record.getPhotos());
    }

    @Test
    public void testTitle(){
        String title = "This is a Title";
        String date = Calendar.getInstance().toString(); // gets the current time.
        String comment = "This is a comment";
        LatLng location = new LatLng(20.914332, 162.191602);
        BodyLocation bodyLocation = BodyLocation.FRONT_CHEST;
        ArrayList<String> photos = new ArrayList<>();

        UserRecord record = new UserRecord(date, comment, "123", location, bodyLocation, photos, title);

        //Test that title is correct
        assertEquals(title, record.getTitle());

        //Change the title
        String newTitle = "This is a new Title";

        //Set the new title
        record.setTitle(newTitle);

        //Test that title is correct after changing
        assertEquals(newTitle, record.getTitle());
    }

    @Test
    public void testDate(){
        String title = "This is a Title";
        String date = Calendar.getInstance().toString(); // gets the current time.
        String comment = "This is a comment";
        LatLng location = new LatLng(20.914332, 162.191602);
        BodyLocation bodyLocation = BodyLocation.FRONT_CHEST;
        ArrayList<String> photos = new ArrayList<>();

        UserRecord record = new UserRecord(date, comment, "123", location, bodyLocation, photos, title);

        //Test that date is correct
        assertEquals(date, record.getDate());

        //Change the Date
        Calendar newDate = Calendar.getInstance(); ;
        String newDateString = newDate.toString(); // gets the current time.

        //Set the new Date
        record.setDate(newDateString);

        //Test that date is correct after changing
        assertEquals(newDateString, record.getDate());
    }

    @Test
    public void testComment(){
        String title = "This is a Title";
        String date = Calendar.getInstance().toString(); // gets the current time.
        String comment = "This is a comment";
        LatLng location = new LatLng(20.914332, 162.191602);
        BodyLocation bodyLocation = BodyLocation.FRONT_CHEST;
        ArrayList<String> photos = new ArrayList<>();

        UserRecord record = new UserRecord(date, comment, "123", location, bodyLocation, photos, title);

        //Test that comment is correct
        assertEquals(comment, record.getComment());

        //Change the Comment
        String newComment = "This is a new comment ";

        //Set the new Date
        record.setComment(newComment);

        //Test that date is correct after changing
        assertEquals(newComment, record.getComment());
    }

    @Test
    public void testLocation(){
        String title = "This is a Title";
        String date = Calendar.getInstance().toString(); // gets the current time.
        String comment = "This is a comment";
        LatLng location = new LatLng(20.914332, 162.191602);
        BodyLocation bodyLocation = BodyLocation.FRONT_CHEST;
        ArrayList<String> photos = new ArrayList<>();

        UserRecord record = new UserRecord(date, comment, "123", location, bodyLocation, photos, title);

        //Test that location is correct
        assertEquals(location, record.getLocation());

        //Change the Location
        LatLng newLocation = new LatLng(10, 18);

        //Set the new location
        record.setLocation(newLocation);

        //Test that location is correct after changing
        assertEquals(newLocation, record.getLocation());


    }

    @Test
    public void testBodyLocation(){

        String title = "This is a Title";
        String date = Calendar.getInstance().toString(); // gets the current time.
        String comment = "This is a comment";

        UserRecord record = new UserRecord(date, comment, "123", null, BodyLocation.BACK_HEAD, null, 1, title);

        //Test that location is correct
        assertEquals(BodyLocation.BACK_HEAD.toString(), record.getBodyLocation().toString());

        //Change the Location
        BodyLocation newBodyLocation = BodyLocation.BACK_HEAD;


        //Set the new location
        record.setBodyLocation(newBodyLocation);

        //Test that location is correct after changing
        assertEquals(newBodyLocation, record.getBodyLocation());
    }
}