package com.example.cmput301f18t26.icare;

import java.util.ArrayList;
import java.util.Calendar;

public class Problem {

    private String id;
    private String title;
    private ArrayList<String> recordIds;
    private Calendar date;
    private String description;

    public Problem(String title, Calendar date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public String getId(){ return this.id; }
    public void setId(String id){ this.id = id; }
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

    public ArrayList<Record> getRecords(){
        return null;
    }
}
