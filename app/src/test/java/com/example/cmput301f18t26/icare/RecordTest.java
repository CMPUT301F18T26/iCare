package com.example.cmput301f18t26.icare;

import org.junit.Test;
import java.util.Calendar;
import static org.junit.Assert.*;

public class RecordTest {

    @Test
    public void testNewProblemConstructor(){
        String title = "Test";
        Calendar date = Calendar.getInstance();
        String comment = "testtest";

        Record r = new Record(title, date, comment);
        assertEquals(r.getTitle(), title);
        assertEquals(r.getDate(), date);
        assertEquals(r.getComment(), comment);

        Record r2 = new Record(title,date, comment);
        assertEquals(r2.getTitle(), title);
        assertEquals(r2.getComment(), comment);
        assertEquals(r2.getDate(), date);
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