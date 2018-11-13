package com.example.cmput301f18t26.icare;

import com.example.cmput301f18t26.icare.Models.Patient;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PatientTest {
    /*
    Class was created to test the Patient model class.
     */

    @Test
    public void testProblemIdsLogicForPatient() {
        // Loading up the values to test
        int role = 0;
        String username = "username";
        String password = "pass123";
        String email = "fake@mail.com";
        String phone = "123-123-1234";

        String first = "123";
        String second = "321";
        String third = "none";

        // First creating a patient
        Patient patient = new Patient(username, password, email, phone, role);
        // Checking if problemIds' length is 0
        assertEquals(patient.getProblemIds().size(), 0);

        // Adding something to the problems
        patient.addProblem(first);
        // Now checking if that was added
        assertEquals(patient.getProblemIds().get(0), first);

        // Now add other ids and checking the length
        patient.addProblem(second);
        patient.addProblem(third);
        // Now checking size
        assertEquals(patient.getProblemIds().size(), 3);

        // Now checking every id is in place
        ArrayList<String> problems = patient.getProblemIds();
        assertEquals(problems.get(0), first);
        assertEquals(problems.get(1), second);
        assertEquals(problems.get(2), third);
        // Now we delete and check
        patient.deleteProblem(second);
        problems = patient.getProblemIds();
        assertEquals(problems.get(0), first);
        assertEquals(problems.get(1), third);

        // Now deleting all and checking to see if size is zero
        patient.deleteProblem(first);
        patient.deleteProblem(third);
        assertEquals(patient.getProblemIds().size(), 0);
    }
}
