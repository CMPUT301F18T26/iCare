package com.example.cmput301f18t26.icare;

import org.junit.Test;
import java.util.Calendar;
import static org.junit.Assert.*;

public class ProblemTest {

    @Test
    public void testGetSet(){
        String title = "Test";
        Calendar date = Calendar.getInstance();
        String description = "This is a test problems";
        Problem p = new Problem(title, date, description);

        assertEquals(p.getTitle(), title);
        title = "New Title";
        p.setTitle(title);
        assertEquals(p.getTitle(), title);

        assertEquals(p.getDate(), date);
        date = Calendar.getInstance();
        p.setDate(date);
        assertEquals(p.getDate(), date);

        assertEquals(p.getDescription(), description);
        description = "New description";
        p.setDescription(description);
        assertEquals(p.getDescription(), description);
    }
}
