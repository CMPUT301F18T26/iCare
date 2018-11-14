package com.example.cmput301f18t26.icare.Models;

import java.text.SimpleDateFormat;
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

    /**
     * Validates if the user has entered the information correctly. I do not currently call it
     * because I'm not sure if the Problems need descriptions, and there may be
     * other things to consider (ie Title < 50 words or something like that). I will deal with
     * validate later.
     * @return
     */
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
     * This is here to get the ListView to display properly. I am using the
     * problems_list_item incorrectly I think, but with the ArrayAdapter
     * setup how it is now, it calls the Problem toString() method. If you leave
     * the default toString() method, it prints out gibberish in the ListView.
     * This makes it print out the correct values.
     * @return
     */
    public String toString(){

        //http://www.ntu.edu.sg/home/ehchua/programming/java/DateTimeCalendar.html
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;      // 0 to 11
        int day = date.get(Calendar.DAY_OF_MONTH);

        String strdate = day + "/" + month + "/" + year;
        return title + "\n" + strdate;
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
