package com.example.cmput301f18t26.icare;

import com.example.cmput301f18t26.icare.Controllers.RecordFactory;
import com.example.cmput301f18t26.icare.Models.BaseRecord;

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
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy @ hh:mm a");
        String formattedDate = dateFormat.format(date);
        String comment = "testtest";
        String problemID = "12345678";

        BaseRecord r = RecordFactory.getRecord(formattedDate, comment, "sdkfljalks123-123da", null, null, null, 0, title);
        assertEquals(r.getTitle(), title);
        assertEquals(r.getDate(), formattedDate);
        assertEquals(r.getComment(), comment);
    }

    @Test
    public void testGetSet() {
        String title = "Test";
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy @ hh:mm a");
        String formattedDate = dateFormat.format(date);
        String comment = "test";
        String problemID = "12345678";

        BaseRecord r = RecordFactory.getRecord(formattedDate, comment, "sdkfljalks123-123da", null, null, null, 0, title);

        assertEquals(r.getTitle(), title);
        title = "New Title";
        r.setTitle(title);
        assertEquals(r.getTitle(), title);
        assertEquals(r.getDate(), formattedDate);


        assertEquals(r.getComment(), comment);
        comment = "New description";
        r.setComment(comment);
        assertEquals(r.getComment(), comment);
    }

}