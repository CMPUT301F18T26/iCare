package com.example.cmput301f18t26.icare;

import com.example.cmput301f18t26.icare.Models.Record;

import org.junit.Test;
import java.util.Calendar;
import static org.junit.Assert.*;

public class RecordTest {

    @Test
    public void testNewRecordConstructor(){
        String title = "Test";
        Calendar date = Calendar.getInstance();
        String comment = "testtest";

        Record r = new Record(title, date, comment);
        assertEquals(r.getTitle(), title);
        assertEquals(r.getDate(), date);
        assertEquals(r.getComment(), comment);
    }

    @Test
    public void testGetSet() {
        String title = "Test";
        Calendar date = Calendar.getInstance();
        String comment = "test";
        Record r = new Record(title, date, comment);

        assertEquals(r.getTitle(), title);
        title = "New Title";
        r.setTitle(title);
        assertEquals(r.getTitle(), title);

        assertEquals(r.getDate(), date);
        date = Calendar.getInstance();
        r.setDate(date);
        assertEquals(r.getDate(), date);

        assertEquals(r.getComment(), comment);
        comment = "New description";
        r.setComment(comment);
        assertEquals(r.getComment(), comment);
    }
}