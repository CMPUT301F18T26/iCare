package com.example.cmput301f18t26.icare;

import org.junit.Test;
import java.util.Calendar;
import static org.junit.Assert.*;

public class RecordTest {

    @Test
    public void testGetSet() {
        String title = "Test";
        Calendar date = Calendar.getInstance();
        String comment = "test";
        Problem p = new Problem(title, date, comment);

        assertEquals(p.getTitle(), title);
        title = "New Title";
        p.setTitle(title);
        assertEquals(p.getTitle(), title);

        assertEquals(p.getDate(), date);
        date = Calendar.getInstance();
        p.setDate(date);
        assertEquals(p.getDate(), date);

        assertEquals(p.getDescription(), comment);
        comment = "New description";
        p.setDescription(comment);
        assertEquals(p.getDescription(), comment);
    }
}