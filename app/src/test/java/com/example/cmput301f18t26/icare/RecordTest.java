package com.example.cmput301f18t26.icare;

import com.example.cmput301f18t26.icare.Models.Record;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class RecordTest {

    @Test
    public void testNewRecordConstructor(){
        String title = "Test";
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        String formattedDate = dateFormat.format(date);
        String comment = "testtest";
        String problemID = "12345678";

        Record r = new Record(title, formattedDate, comment,problemID);
        assertEquals(r.getTitle(), title);
        assertEquals(r.getDate(), formattedDate);
        assertEquals(r.getComment(), comment);
    }

    @Test
    public void testGetSet() {
        String title = "Test";
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);
        String comment = "test";
        String problemID = "12345678";

        Record r = new Record(title, formattedDate, comment,problemID);

        assertEquals(r.getTitle(), title);
        title = "New Title";
        r.setTitle(title);
        assertEquals(r.getTitle(), title);
        assertEquals(r.getDate(), date.toString());


        assertEquals(r.getComment(), comment);
        comment = "New description";
        r.setComment(comment);
        assertEquals(r.getComment(), comment);
    }

}