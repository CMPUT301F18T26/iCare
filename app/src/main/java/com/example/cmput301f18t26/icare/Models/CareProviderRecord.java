package com.example.cmput301f18t26.icare.Models;

import java.util.ArrayList;

/**
 * Subclass of UserRecord. Meant to store doctor's comments. recordType = 1
 */
public class CareProviderRecord extends BaseRecord {
    /**
     * Constructor for this class
     */
    public CareProviderRecord(String title, String date, String comment, String problemID){
        super(title, date, comment, problemID);
        super.setRecType(1);
    }
}
