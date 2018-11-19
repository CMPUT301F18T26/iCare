package com.example.cmput301f18t26.icare.Controllers;

import android.location.Location;

import com.example.cmput301f18t26.icare.BodyLocation;
import com.example.cmput301f18t26.icare.Models.Record;
import com.example.cmput301f18t26.icare.Models.UserRecord;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This factor produces records, either UserRecord or Record is returned depending on the type of record that is needed.
 */
public class RecordFactory {
    /**
     * Takes a list of parameters and returns either a record or UserRecord.
     * @param title
     * @param date
     * @param comment
     * @param problemID
     * @param location
     * @param bodyLocation
     * @param photos
     * @return
     */
    public static Record getRecord(String title, String date, String comment, String problemID, Location location, BodyLocation bodyLocation, ArrayList<String> photos){
        Record record;
        // Depending on what distinguishes a UserRecord from a Record or other types of record,
        // use case statements to determine what type of Record to construct
        // always just return a record to keep code polymorphic
        if (true) {
            record = new UserRecord(title, date, comment, problemID,null, null,null);
        } else {
            // same thing as above but lets pretend we could also make a record
            record = new Record(title, date, comment, problemID);
        }
        return record;
    }
}