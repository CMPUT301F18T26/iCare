package com.example.cmput301f18t26.icare.Controllers;

import com.example.cmput301f18t26.icare.Models.Record;

import java.util.Calendar;

public class RecordFactory {
    public static Record getRecord(String title, String date,String description, String problemUID){
        Record record;
        record = new Record(title,date,description,problemUID);
        return record;
    }
}
