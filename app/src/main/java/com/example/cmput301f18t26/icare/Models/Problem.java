package com.example.cmput301f18t26.icare.Models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class Problem {

    //UID stands for Unique ID
    private final String UID;
    private String title;
    //private ArrayList<String> recordIds;
    private Calendar date;
    private String description;
    private String userUID;

    public Problem(String title, Calendar date, String description, String userUID) {
        this.UID = UUID.randomUUID().toString();
        this.userUID = userUID;
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public boolean validate() {
        if (this.title.isEmpty() || this.date == null || this.description.isEmpty()) {
            // IF ANY FIELD IS EMPTY
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Getters and Setters for Problem
     * @return
     */
    public String getUserUID(){
        return this.userUID;
    }

    public String getUID(){ return this.UID; }
    public String getTitle(){ return this.title; }
    public void setTitle(String title) { this.title = title; }
    public Calendar getDate() { return this.date; }
    public void setDate(Calendar date) { this.date = date; }
    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public void addRecord(String rid){

    }

    public void removeRecord(String rid){

    }

    public Record getRecord(String rid){
        return null;
    }

    public Record getRecord(int i) {
        return null;
    }

    public ArrayList<Record> getRecords(){
        return null;
    }
}
