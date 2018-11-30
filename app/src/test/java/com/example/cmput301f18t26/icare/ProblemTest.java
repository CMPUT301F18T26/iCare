package com.example.cmput301f18t26.icare;

import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.example.cmput301f18t26.icare.Models.UserRecord;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class ProblemTest {
    /**
     * Test was created to test the getters and setters
     */
    @Test
    public void testGetSet(){
        String title = "Test";
        Calendar date = Calendar.getInstance();
        String description = "This is a test problems";
        Problem p = new Problem(title, date, description, "1");

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
