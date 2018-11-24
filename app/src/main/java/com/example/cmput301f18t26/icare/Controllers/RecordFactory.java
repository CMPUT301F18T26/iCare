package com.example.cmput301f18t26.icare.Controllers;

import android.location.Location;

import com.example.cmput301f18t26.icare.BodyLocation;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.CareProviderRecord;
import com.example.cmput301f18t26.icare.Models.UserRecord;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This factor produces records, either UserRecord or Record is returned depending on the type of record that is needed.
 */
public class RecordFactory {
    /**
     * Takes a list of parameters and returns either a record or UserRecord.
     *
     * @param date
     * @param comment
     * @param problemUID
     * @param location
     * @param bodyLocation
     * @param photos
     * @param title
     * @return
     */
    public static BaseRecord getRecord(String date, String comment, String problemUID, Location location, BodyLocation bodyLocation, ArrayList<String> photos, int recType, String title){
        BaseRecord record;
        // Depending on what distinguishes a UserRecord from a Record or other types of record,
        // use case statements to determine what type of Record to construct
        // always just return a record to keep code polymorphic
        if (recType == 0) {
            record = new CareProviderRecord(date, comment, problemUID, title);
        } else {
            // same thing as above but lets pretend we could also make a record
            record = new UserRecord(date, comment, problemUID,null, null,null, title);
        }
        return record;
    }
}