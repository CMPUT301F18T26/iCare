package com.example.cmput301f18t26.icare;

import android.location.Location;
//import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
//import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import static org.junit.Assert.*;
//@RunWith(AndroidJUnit4.class)
public class UserRecordTest {

    @Test
    public void testNewRecordConstructor() {
        // Loading up the values to test
        String id = "id_for_record";
        String title = "Title";
        Calendar date = Calendar.getInstance(); // gets the current time.
        String comment = "This is a comment";
        Location location = new Location("testProvider");
        location.setLatitude(20.914332);//20.914332, 162.191602 -- Pacific Ocean
        location.setLongitude(162.191602);
        String bodyLocation = "Head";
        ArrayList<String> photos = new ArrayList<>();


        // First creating a userRecord
        UserRecord record = new UserRecord(title, date, comment, location, bodyLocation, photos);

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
        Calendar date = Calendar.getInstance(); // gets the current time.
        String comment = "This is a comment";
        Location location = new Location("testProvider");
        location.setLatitude(20.914332);//20.914332, 162.191602 -- Pacific Ocean
        location.setLongitude(162.191602);
        String bodyLocation = "Head";
        ArrayList<String> photos = new ArrayList<>();

        UserRecord record = new UserRecord(title, date, comment, location, bodyLocation, photos);

        //Test that title is correct
        assertEquals(title, record.getTitle());

        //Change the title
        String newTitle = "This is a new Title";

        //Set the new title
        record.setTitle(newTitle);

        //Test that title is correct after changing
        assertEquals(title, record.getTitle());

    }

    @Test
    public void testDate(){

        String title = "This is a Title";
        Calendar date = Calendar.getInstance(); // gets the current time.
        String comment = "This is a comment";
        Location location = new Location("testProvider");
        location.setLatitude(20.914332);//20.914332, 162.191602 -- Pacific Ocean
        location.setLongitude(162.191602);
        String bodyLocation = "Head";
        ArrayList<String> photos = new ArrayList<>();

        UserRecord record = new UserRecord(title, date, comment, location, bodyLocation, photos);

        //Test that date is correct
        assertEquals(date, record.getDate());

        //Change the title
        String newTitle = "This is a new Title";

        //Set the new title
        record.setTitle(newTitle);

        //Test that title is correct after changing
        assertEquals(title, record.getTitle());

    }





}