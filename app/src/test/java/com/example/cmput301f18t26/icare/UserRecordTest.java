package com.example.cmput301f18t26.icare;

import android.location.Location;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Calendar;
import static org.junit.Assert.*;

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
        assertEquals(newTitle, record.getTitle());

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

        //Change the Date
        Calendar newDate = Calendar.getInstance(); ;

        //Set the new Date
        record.setDate(newDate);

        //Test that date is correct after changing
        assertEquals(newDate, record.getDate());

    }

    @Test
    public void testComment(){

        String title = "This is a Title";
        Calendar date = Calendar.getInstance(); // gets the current time.
        String comment = "This is a comment";
        Location location = new Location("testProvider");
        location.setLatitude(20.914332);//20.914332, 162.191602 -- Pacific Ocean
        location.setLongitude(162.191602);
        String bodyLocation = "Head";
        ArrayList<String> photos = new ArrayList<>();

        UserRecord record = new UserRecord(title, date, comment, location, bodyLocation, photos);

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
        Calendar date = Calendar.getInstance(); // gets the current time.
        String comment = "This is a comment";
        Location location = new Location("testProvider");
        location.setLatitude(20.914332);//20.914332, 162.191602 -- Pacific Ocean
        location.setLongitude(162.191602);
        String bodyLocation = "Head";
        ArrayList<String> photos = new ArrayList<>();

        UserRecord record = new UserRecord(title, date, comment, location, bodyLocation, photos);

        //Test that location is correct
        assertEquals(location, record.getLocation());

        //Change the Location
        Location newLocation = new Location("testProvider");
        location.setLatitude(10);
        location.setLongitude(18);

        //Set the new location
        record.setLocation(newLocation);

        //Test that location is correct after changing
        assertEquals(newLocation, record.getLocation());


    }

//    @Test
//    public void testBodyLocation(){
//
//        String title = "This is a Title";
//        Calendar date = Calendar.getInstance(); // gets the current time.
//        String comment = "This is a comment";
//        Location location = new Location("testProvider");
//        location.setLatitude(20.914332);//20.914332, 162.191602 -- Pacific Ocean
//        location.setLongitude(162.191602);
//        String bodyLocation = "Head";
//        ArrayList<String> photos = new ArrayList<>();
//
//        UserRecord record = new UserRecord(title, date, comment, location, bodyLocation, photos);
//
//        //Test that location is correct
//        assertEquals(location, record.getLocation());
//
//        //Change the Location
//        Location newLocation = new Location("testProvider");
//        location.setLatitude(10);
//        location.setLongitude(18);
//
//        //Set the new location
//        record.setLocation(newLocation);
//
//        //Test that location is correct after changing
//        assertEquals(newLocation, record.getLocation());
//
//
//    }

    @Test
    public void testPhotos() {
        String title = "This is a Title";
        Calendar date = Calendar.getInstance(); // gets the current time.
        String comment = "This is a comment";
        Location location = new Location("testProvider");
        location.setLatitude(20.914332);//20.914332, 162.191602 -- Pacific Ocean
        location.setLongitude(162.191602);
        String bodyLocation = "Head";
        ArrayList<String> photos = new ArrayList<>();

        UserRecord record = new UserRecord(title, date, comment, location, bodyLocation, photos);
        // Checking if photos length is 0
        assertEquals(photos.size(), 0);

        // Adding something to photos
        record.addPhoto("test.jpg");
        // Now checking if that was added
        assertEquals(record.getPhotos().get(0), "test.jpg");

        // Now checking size
        assertEquals(record.getPhotos().size(), 1);

        // Now we delete and check
        record.removePhoto("test.jpg");
        assertEquals(record.getPhotos().size(), 0);
    }

}