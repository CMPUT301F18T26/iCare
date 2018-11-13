package com.example.cmput301f18t26.icare;

import com.example.cmput301f18t26.icare.Models.CareProvider;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CareProviderTest {
    @Test
    public void testPatientLogicForCareProvider() {
        // Loading up the values to test
        String id = "id_for_this_thing";
        String username = "username";
        String password = "pass123";
        String email = "fake@mail.com";
        String phone = "123-123-1234";

        String first = "123";
        String second = "321";
        String third = "none";

        // First creating a patient
        CareProvider careProvider = new CareProvider(id, username, password, email, phone);
        // Checking if problemIds' length is 0
        assertEquals(careProvider.getPatientIds().size(), 0);

        // Adding something to the problems
        careProvider.addPatient(first);
        // Now checking if that was added
        assertEquals(careProvider.getPatientIds().get(0), first);

        // Now add other ids and checking the length
        careProvider.addPatient(second);
        careProvider.addPatient(third);
        // Now checking size
        assertEquals(careProvider.getPatientIds().size(), 3);

        // Now checking every id is in place
        ArrayList<String> problems = careProvider.getPatientIds();
        assertEquals(problems.get(0), first);
        assertEquals(problems.get(1), second);
        assertEquals(problems.get(2), third);
        // Now we delete and check
        careProvider.deletePatient(second);
        problems = careProvider.getPatientIds();
        assertEquals(problems.get(0), first);
        assertEquals(problems.get(1), third);

        // Now deleting all and checking to see if size is zero
        careProvider.deletePatient(first);
        careProvider.deletePatient(third);
        assertEquals(careProvider.getPatientIds().size(), 0);
    }
}
