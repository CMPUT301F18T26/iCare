package com.example.cmput301f18t26.icare.Models;

/**
 * Subclass of UserRecord. Meant to store doctor's comments. recordType = 1
 */
public class CareProviderRecord extends BaseRecord {
    /**
     * Constructor for this class
     */
    public CareProviderRecord(String date, String comment, String problemID, String title){
        super(date, comment, problemID, title);
        super.setRecType(1);
    }
}
