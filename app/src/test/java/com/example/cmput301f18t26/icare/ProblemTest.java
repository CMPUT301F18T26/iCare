package com.example.cmput301f18t26.icare;

import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.Record;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import static org.junit.Assert.*;

public class ProblemTest {

    private Problem getDefaultTestProblem(){
        return new Problem("Test", Calendar.getInstance(), "This is a test problem");
    }

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

    @Test
    public void testAddRecords(){
        Problem p = getDefaultTestProblem();
        Record r = new Record("Test", Calendar.getInstance(), "This is a test record");
        p.addRecord(r.getId());
        assertEquals(p.getRecord(r.getId()), r);
    }

    @Test
    public void testRemoveRecord(){
        Problem p = getDefaultTestProblem();
        Record r = new Record("Test", Calendar.getInstance(), "This is a test record");
        String rid = r.getId();
        p.addRecord(rid);
        p.removeRecord(rid);
        assertEquals(p.getRecord(rid), null);
    }

    @Test
    public void testGetRecords(){
        Problem p = getDefaultTestProblem();
        Record r1 = new Record("Test1", Calendar.getInstance(), "This is a test record");
        Record r2 = new Record("Test2", Calendar.getInstance(), "This is a test record");
        ArrayList<Record> rlist = new ArrayList<>();

        assertEquals(rlist, p.getRecords());
        p.addRecord(r1.getId());
        rlist.add(r1);
        assertEquals(rlist, p.getRecords());
        p.addRecord(r2.getId());
        rlist.add(r2);
        assertEquals(rlist, p.getRecords());
    }
}
